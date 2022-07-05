package com.oshovskii.movie.service.implementations;

import com.oshovskii.movie.repositories.UserRepository;
import com.oshovskii.movie.service.MovieService;
import com.oshovskii.movie.service.UserService;
import com.oshovskii.movie.utils.UserUtils;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.oshovskii.movie.factory.TestDtoFactory.createUpdateUserNameRequest;
import static com.oshovskii.movie.factory.TestDtoFactory.createUpdateUserUsernameRequest;
import static com.oshovskii.movie.factory.TestEntityFactory.createMovie;
import static com.oshovskii.movie.factory.TestEntityFactory.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(UserServiceImpl.class)
@DisplayName("UserService test")
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepositoryMock;

    @MockBean
    private MovieService movieServiceMock;

    @Test
    @DisplayName("findByUsername() " +
            "with valid username " +
            "should return Optional<User> test")
    void findByUsername_validUsername_shouldReturnOptionalUser() {
        // Config
        val sourceUsername = "username";
        val targetUser = createUser(1);

        when(userRepositoryMock.findByUsername(sourceUsername)).thenReturn(Optional.of(targetUser));

        // Call
        val actualUser = userRepositoryMock.findByUsername(sourceUsername);

        // Verify
        assertThat(actualUser).isPresent().get().usingRecursiveComparison().isEqualTo(targetUser);

        verify(userRepositoryMock).findByUsername(sourceUsername);
    }

    @Test
    @DisplayName("findById() " +
            "with valid user id " +
            "should return Optional<User> test")
    void findById_validUserId_shouldReturnOptionalUser() {
        // Config
        val sourceUserId = 1L;
        val targetUser = createUser(1);

        when(userRepositoryMock.findById(sourceUserId)).thenReturn(Optional.of(targetUser));

        // Call
        val actualUser = userRepositoryMock.findById(sourceUserId);

        // Verify
        assertThat(actualUser).isPresent().get().usingRecursiveComparison().isEqualTo(targetUser);

        verify(userRepositoryMock).findById(sourceUserId);
    }

    @Test
    @DisplayName("loadUserByUsername() " +
            "with valid username " +
            "should return UserDetails test")
    void loadUserByUsername_validUsername_shouldReturnUserDetails() {
        // Config
        val sourceUsername = "username";
        val targetUser = createUser(1);
        val targetUserDetails = new org.springframework.security.core.userdetails.User(
                targetUser.getUsername(),
                targetUser.getPassword(),
                UserUtils.mapRolesToAuthorities(targetUser.getRoles())
        );

        when(userRepositoryMock.findByUsername(sourceUsername)).thenReturn(Optional.of(targetUser));

        // Call
        val actualUserDetails = userService.loadUserByUsername(sourceUsername);

        // Verify
        assertEquals(targetUserDetails, actualUserDetails);

        verify(userRepositoryMock).findByUsername(sourceUsername);
    }

    @Test
    @DisplayName("updateUsername() " +
            "with valid UpdateUserUsernameRequest " +
            "should return User test")
    void updateUsername_validUpdateUserUsernameRequest_shouldReturnUser() {
        // Config
        val sourceUpdateUserUsernameRequest = createUpdateUserUsernameRequest(1);
        val sourceUser = createUser(1);
        sourceUser.setUsername(sourceUpdateUserUsernameRequest.getUsername());

        val targetUser = createUser(2);

        when(userRepositoryMock.findByEmail(sourceUpdateUserUsernameRequest.getEmail())).thenReturn(Optional.of(sourceUser));

        when(userRepositoryMock.save(sourceUser)).thenReturn(targetUser);

        // Call
        val actualUser = userService.updateUsername(sourceUpdateUserUsernameRequest);

        // Verify
        assertEquals(targetUser, actualUser);

        verify(userRepositoryMock).findByEmail(sourceUpdateUserUsernameRequest.getEmail());

        verify(userRepositoryMock).save(sourceUser);
    }

    @Test
    @DisplayName("updateName() " +
            "with valid UpdateUserUsernameRequest " +
            "should return User test")
    void updateName_validUpdateUserUsernameRequest_shouldReturnUser() {
        // Config
        val sourceUpdateUserNameRequest = createUpdateUserNameRequest(1);
        val sourceUser = createUser(1);
        sourceUser.setName(sourceUpdateUserNameRequest.getName());

        val targetUser = createUser(2);

        when(userRepositoryMock.findByEmail(sourceUpdateUserNameRequest.getEmail())).thenReturn(Optional.of(sourceUser));

        when(userRepositoryMock.save(sourceUser)).thenReturn(targetUser);

        // Call
        val actualUser = userService.updateName(sourceUpdateUserNameRequest);

        // Verify
        assertEquals(targetUser, actualUser);

        verify(userRepositoryMock).findByEmail(sourceUpdateUserNameRequest.getEmail());

        verify(userRepositoryMock).save(sourceUser);
    }

    @Test
    @DisplayName("deleteById() " +
            "with valid user id " +
            "should void output test")
    void deleteById_validUserId_voidOutput() {
        // Config
        val sourceUserId = 1L;

        doNothing().when(userRepositoryMock).deleteById(sourceUserId);

        // Call
        userService.deleteById(sourceUserId);

        // Verify
        verify(userRepositoryMock).deleteById(sourceUserId);
    }

    @Test
    @DisplayName("addMovieToFavorite() " +
            "with valid user id and movie id" +
            "should return Movie test")
    void addMovieToFavorite_validUserIdAndMovieId_shouldReturnMovie() {
        // Config
        val sourceUserId = "1";
        val sourceMovieId = 1L;

        val targetUserId = 1L;
        val targetUser1 = createUser(1);
        val targetUser2 = createUser(2);

        val targetMovie = createMovie(1);
        targetUser1.getFavorites().add(targetMovie);

        when(userRepositoryMock.findById(targetUserId)).thenReturn(Optional.of(targetUser1));

        when(movieServiceMock.findById(sourceMovieId)).thenReturn(targetMovie);

        when(userRepositoryMock.save(targetUser1)).thenReturn(targetUser2);

        // Call
        val actualMovie = userService.addMovieToFavorite(sourceMovieId, sourceUserId);

        // Verify
        assertEquals(targetMovie, actualMovie);

        verify(userRepositoryMock).findById(targetUserId);

        verify(movieServiceMock).findById(targetUserId);

        verify(userRepositoryMock).save(targetUser1);
    }

    @Test
    @DisplayName("deleteMovieFromFavorite() " +
            "with valid user id and movie id" +
            "should void output test")
    void deleteMovieFromFavorite_validUserIdAndMovieId_voidOutput() {
        // Config
        val sourceUserId = "1";
        val sourceMovieId = 1L;

        val targetUserId = 1L;
        val targetUser1 = createUser(1);
        val targetUser2 = createUser(2);

        val targetMovie = createMovie(1);
        targetUser1.getFavorites().remove(targetMovie);

        when(userRepositoryMock.findById(targetUserId)).thenReturn(Optional.of(targetUser1));

        when(movieServiceMock.findById(sourceMovieId)).thenReturn(targetMovie);

        when(userRepositoryMock.save(targetUser1)).thenReturn(targetUser2);

        // Call
        userService.deleteMovieFromFavorite(sourceMovieId, sourceUserId);

        // Verify
        verify(userRepositoryMock).findById(targetUserId);

        verify(movieServiceMock).findById(targetUserId);

        verify(userRepositoryMock).save(targetUser1);
    }
}
