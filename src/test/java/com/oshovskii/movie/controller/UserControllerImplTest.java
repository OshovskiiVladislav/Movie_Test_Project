package com.oshovskii.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oshovskii.movie.dto.movies.MovieDto;
import com.oshovskii.movie.dto.users.UserDto;
import com.oshovskii.movie.service.UserService;
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
import static com.oshovskii.movie.factory.TestEntityFactory.createMovie;
import static com.oshovskii.movie.factory.TestEntityFactory.createUser;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class, useDefaultFilters = false)
@Import(UserControllerImpl.class)
@DisplayName("UserController test")
class UserControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ModelMapper modelMapperMock;

    @MockBean
    private UserService userServiceMock;

    @Test
    @DisplayName("updateUsername() " +
            "with valid UpdateUserUsernameRequest " +
            "should return UserDto test")
    void updateUsername_validUpdateUserUsernameRequest_shouldReturnUserDto() throws Exception {
        // Config
        val sourceUpdateUserUsernameRequest = createUpdateUserUsernameRequest(1);
        val targetUser = createUser(1);
        val targetUserDto = createUserDto(1);

        when(userServiceMock.updateUsername(sourceUpdateUserUsernameRequest)).thenReturn(targetUser);

        when(modelMapperMock.map(targetUser, UserDto.class)).thenReturn(targetUserDto);

        val sourceJson = objectMapper.writeValueAsString(sourceUpdateUserUsernameRequest);
        val targetJson = objectMapper.writeValueAsString(targetUserDto);

        // Call and Verify
        mockMvc.perform(post("/api/v1/users/update/username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());

        verify(userServiceMock).updateUsername(sourceUpdateUserUsernameRequest);
        verify(modelMapperMock).map(targetUser, UserDto.class);
    }

    @Test
    @DisplayName("updateName() " +
            "with valid UpdateUserNameRequest " +
            "should return UserDto test")
    void updateName_validUpdateUserNameRequest_shouldReturnUserDto() throws Exception {
        // Config
        val sourceUpdateUserNameRequest = createUpdateUserNameRequest(1);
        val targetUser = createUser(1);
        val targetUserDto = createUserDto(1);

        when(userServiceMock.updateName(sourceUpdateUserNameRequest)).thenReturn(targetUser);

        when(modelMapperMock.map(targetUser, UserDto.class)).thenReturn(targetUserDto);

        val sourceJson = objectMapper.writeValueAsString(sourceUpdateUserNameRequest);
        val targetJson = objectMapper.writeValueAsString(targetUserDto);

        // Call and Verify
        mockMvc.perform(post("/api/v1/users/update/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sourceJson))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());

        verify(userServiceMock).updateName(sourceUpdateUserNameRequest);
        verify(modelMapperMock).map(targetUser, UserDto.class);
    }

    @Test
    @DisplayName("deleteUser() " +
            "with valid user id " +
            "should void output test")
    void deleteUser_validUserId_voidOutput() throws Exception {
        // Config
        val sourceUserId = 1L;

        doNothing().when(userServiceMock).deleteById(sourceUserId);

        // Call and Verify
        mockMvc.perform(delete("/api/v1/users/{userId}", sourceUserId))
                .andExpect(status().isOk());

        verify(userServiceMock).deleteById(sourceUserId);
    }

    @Test
    @DisplayName("addMovieToFavorite() " +
            "with valid MovieToFavoriteRequest and header User-Id " +
            "should return MovieDto test")
    void addMovieToFavorite_validMovieToFavoriteRequestAndHeaderUserId_shouldReturnMovieDto() throws Exception {
        // Config
        val headerUserId = "1";
        val sourceMovieId = 1L;

        val targetMovie = createMovie(1);
        targetMovie.setId(sourceMovieId);

        val targetMovieDto = createMovieDto(1);
        targetMovieDto.setId(sourceMovieId);

        when(userServiceMock.addMovieToFavorite(sourceMovieId, headerUserId)).thenReturn(targetMovie);

        when(modelMapperMock.map(targetMovie, MovieDto.class)).thenReturn(targetMovieDto);

        val sourceJson = objectMapper.writeValueAsString(sourceMovieId);
        val targetJson = objectMapper.writeValueAsString(targetMovieDto);

        // Call and Verify
        mockMvc.perform(put("/api/v1/users/favorite/add/{movieId}", sourceMovieId)
                        .header("User-Id", headerUserId))
                .andExpect(content().string(targetJson))
                .andExpect(status().isOk());

        verify(userServiceMock).addMovieToFavorite(sourceMovieId, headerUserId);

        verify(modelMapperMock).map(targetMovie, MovieDto.class);
    }

    @Test
    @DisplayName("deleteMovieFromFavorite() " +
            "with valid MovieFromFavoriteRequest and header User-Id " +
            "should return MovieDto test")
    void deleteMovieFromFavorite() throws Exception {
        // Config
        val headerUserId = "1";
        val sourceMovieId = 1L;

        doNothing().when(userServiceMock).deleteMovieFromFavorite(sourceMovieId, headerUserId);

        // Call and Verify
        mockMvc.perform(delete("/api/v1/users/favorite/del/{movieId}", sourceMovieId)
                        .header("User-Id", headerUserId))
                .andExpect(status().isOk());

        verify(userServiceMock).deleteMovieFromFavorite(sourceMovieId, headerUserId);
    }
}
