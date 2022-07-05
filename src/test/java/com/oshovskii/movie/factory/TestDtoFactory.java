package com.oshovskii.movie.factory;

import com.oshovskii.movie.dto.auth.AuthRequestDto;
import com.oshovskii.movie.dto.auth.AuthResponseDto;
import com.oshovskii.movie.dto.auth.SignUpRequestDto;
import com.oshovskii.movie.dto.movies.Docs;
import com.oshovskii.movie.dto.movies.KinopoiskMovieDto;
import com.oshovskii.movie.dto.movies.MovieDto;
import com.oshovskii.movie.dto.users.UpdateUserNameRequest;
import com.oshovskii.movie.dto.users.UpdateUserUsernameRequest;
import com.oshovskii.movie.dto.users.UserDto;

import java.util.Collection;
import java.util.Collections;

public class TestDtoFactory {

    public static UserDto createUserDto(int index) {
        return new UserDto(
                (long) index,
                "username",
                "password_" + index,
                "test_email@gmail_" + index
        );
    }

    public static SignUpRequestDto createSignUpRequestDto(int index) {
        return new SignUpRequestDto(
                "email_test_@gmail.com_" + index,
                "password_test_" + index,
                "username",
                "name_" + index
        );
    }

    public static AuthRequestDto createAuthRequestDto(int index) {
        return new AuthRequestDto(
                "username",
                "password_test_" + index
        );
    }

    public static AuthResponseDto createAuthResponseDto(int index) {
        return new AuthResponseDto(
                (long) index,
                "password_test_" + index,
                "username",
                "name_" + index
        );
    }

    public static MovieDto createMovieDto(int index) {
        return new MovieDto(
                (long) index,
                "title_test_" + index,
                "poster_path_test_" + index
        );
    }

    public static UpdateUserUsernameRequest createUpdateUserUsernameRequest(int index) {
        return new UpdateUserUsernameRequest(
                "username",
                "email_@test_" + index
        );
    }

    public static UpdateUserNameRequest createUpdateUserNameRequest(int index) {
        return new UpdateUserNameRequest(
                "name_" + index,
                "email_@test_" + index
        );
    }
}
