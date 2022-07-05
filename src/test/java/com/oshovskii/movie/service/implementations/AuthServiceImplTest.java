package com.oshovskii.movie.service.implementations;

import com.oshovskii.movie.service.AuthService;
import com.oshovskii.movie.service.RoleService;
import com.oshovskii.movie.service.UserService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

import static com.oshovskii.movie.factory.TestDtoFactory.createAuthRequestDto;
import static com.oshovskii.movie.factory.TestEntityFactory.createRole;
import static com.oshovskii.movie.factory.TestEntityFactory.createUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(AuthServiceImpl.class)
@DisplayName("AuthService test")
class AuthServiceImplTest {

    @Autowired
    private AuthService authService;

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private PasswordEncoder passwordEncoderMock;

    @MockBean
    private AuthenticationManager authenticationManagerMock;

    @MockBean
    private RoleService roleServiceMock;

    @Test
    @DisplayName("recoverUserId() " +
            "with valid AuthRequestDto " +
            "should return User test")
    void recoverUserId_validAuthRequestDto_shouldReturnUser() {
        // Config
        val sourceAuthRequestDto = createAuthRequestDto(1);
        val targetUser = createUser(1);


        when(authenticationManagerMock.authenticate(new UsernamePasswordAuthenticationToken(
                sourceAuthRequestDto.getUsername(),
                sourceAuthRequestDto.getPassword())
        )).thenReturn(new UsernamePasswordAuthenticationToken(
                sourceAuthRequestDto.getUsername(),
                sourceAuthRequestDto.getPassword()));

        when(userServiceMock.findByUsername(sourceAuthRequestDto.getUsername())).thenReturn(Optional.of(targetUser));

        // Call
        val actualUser = authService.recoverUserId(sourceAuthRequestDto);

        // Verify
        assertEquals(targetUser, actualUser);

        verify(userServiceMock).findByUsername(sourceAuthRequestDto.getUsername());
    }

    @Test
    @DisplayName("saveNewUser() " +
            "with valid User " +
            "should return saved User test")
    void saveNewUser_validUser_shouldReturnSavedUser() {
        // Config
        val sourceUser = createUser(1);
        val defaultUserRole = "ROLE_USER";
        val encodePassword = "test";

        val targetRole = createRole(1);
        targetRole.setName(defaultUserRole);

        val targetUser = createUser(2);
        targetUser.setRoles(Set.of(targetRole));
        targetUser.setPassword(encodePassword);

        when(userServiceMock.save(sourceUser)).thenReturn(targetUser);

        when(roleServiceMock.getByName(defaultUserRole)).thenReturn(targetRole);

        when(passwordEncoderMock.encode(sourceUser.getPassword())).thenReturn(encodePassword);

        // Call
        val actualUser = authService.saveNewUser(sourceUser);

        // Verify
        assertEquals(targetUser, actualUser);

        verify(userServiceMock).save(sourceUser);
    }
}
