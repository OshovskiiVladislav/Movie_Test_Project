package com.oshovskii.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.movie.dto.users.UserDto;
import com.oshovskii.movie.model.User;
import com.oshovskii.movie.service.AuthService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.oshovskii.movie.factory.TestDtoFactory.*;
import static com.oshovskii.movie.factory.TestEntityFactory.createUser;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class, useDefaultFilters = false)
@Import(AuthControllerImpl.class)
@DisplayName("AuthController test")
class AuthControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModelMapper modelMapperMock;

    @MockBean
    private AuthService authServiceMock;

    @Test
    @DisplayName("signUp() " +
            "with valid signUpRequestDto " +
            "should sign up and return UserDto test")
    void signUp_validSignUpRequestDto_shouldReturnUserDto() throws Exception {
        // Config
        val sourceSignUpRequestDto = createSignUpRequestDto(1);
        val sourceUser = createUser(1);
        val targetUser = createUser(2);
        val targetUserAfterMapToDto = createUserDto(3);

        val sourceJson = objectMapper.writeValueAsString(sourceSignUpRequestDto);
        val targetJson = objectMapper.writeValueAsString(targetUserAfterMapToDto);

        when(modelMapperMock.map(sourceSignUpRequestDto, User.class)).thenReturn(sourceUser);

        when(authServiceMock.saveNewUser(sourceUser)).thenReturn(targetUser);

        when(modelMapperMock.map(targetUser, UserDto.class)).thenReturn(targetUserAfterMapToDto);

        // Call and verify
        mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());

        verify(authServiceMock).saveNewUser(sourceUser);
    }

    @Test
    @DisplayName("recoverUserId() " +
            "with valid authRequestDto " +
            "should return ReturnAuthResponseDto test")
    void recoverUserId_validAuthRequestDto_shouldReturnAuthResponseDto() throws Exception {
        // Config
        val userId = 1;
        val sourceAuthRequestDto = createAuthRequestDto(1);
        val targetUser = createUser(1);
        targetUser.setId((long) userId);

        when(authServiceMock.recoverUserId(sourceAuthRequestDto)).thenReturn(targetUser);

        val sourceJson = objectMapper.writeValueAsString(sourceAuthRequestDto);

        // Call and verify
        mockMvc.perform(post("/api/v1/auth/recover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(jsonPath("$.userId", is(userId)))
                .andExpect(jsonPath("$.password", is(targetUser.getPassword())))
                .andExpect(jsonPath("$.username", is(targetUser.getUsername())))
                .andExpect(jsonPath("$.name", is(targetUser.getName())))
                .andExpect(status().isOk());

        verify(authServiceMock).recoverUserId(sourceAuthRequestDto);
    }
}