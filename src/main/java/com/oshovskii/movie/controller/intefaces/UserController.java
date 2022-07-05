package com.oshovskii.movie.controller.intefaces;

import com.oshovskii.movie.dto.movies.MovieDto;
import com.oshovskii.movie.dto.users.UpdateUserNameRequest;
import com.oshovskii.movie.dto.users.UpdateUserUsernameRequest;
import com.oshovskii.movie.dto.users.UserDto;
import io.swagger.annotations.*;

@Api(value = "/api/v1/users", tags = "Пользователи", produces = "application/json")
public interface UserController {

    @ApiOperation(
            value = "Изменение ника пользователя",
            notes = "Endpoint для изменения ника пользователя",
            httpMethod = "POST"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "", response = UserDto.class)
            }
    )
    UserDto updateUsername(@ApiParam(value = "Модель запроса на изменение никнейма", required = true)
                                     UpdateUserUsernameRequest request);

    @ApiOperation(
            value = "Изменение имени пользователя",
            notes = "Endpoint для изменения имени пользователя",
            httpMethod = "POST"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "", response = UserDto.class)
            }
    )
    UserDto updateName(@ApiParam(value = "Модель запроса на изменение имени", required = true)
                                 UpdateUserNameRequest request);

    @ApiOperation(
            value = "Удаление пользователя по ID",
            notes = "Endpoint удаления пользователя по ID",
            httpMethod = "DELETE"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = ""),
                    @ApiResponse(code = 404, message = "User not found by ID: [id]")
            }
    )
    void deleteUser(@ApiParam(value = "Идентификатор пользователя") Long userId);

    @ApiOperation(
            value = "Добавление фильма в избранное пользователя по ID фильма",
            notes = "Endpoint добавления фильма в избранное пользователя по ID фильма",
            httpMethod = "DELETE"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = ""),
                    @ApiResponse(code = 404, message = "User not found by ID: [id]"),
                    @ApiResponse(code = 404, message = "Movie not found by ID: [id]")
            }
    )
    MovieDto addMovieToFavorite(@ApiParam(value = "Идентификатор фильма") Long movieId,
                                @ApiParam(value = "Заголовок User-Id") String userId);

    @ApiOperation(
            value = "Удаление фильма из списка избранного пользователя по ID фильма",
            notes = "Endpoint удаления фильма из списка избранного пользователя по ID фильма",
            httpMethod = "DELETE"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = ""),
                    @ApiResponse(code = 404, message = "User not found by ID: [id]"),
                    @ApiResponse(code = 404, message = "Movie not found by ID: [id]")
            }
    )
    void deleteMovieFromFavorite(@ApiParam(value = "Идентификатор фильма") Long movieId,
                                 @ApiParam(value = "Заголовок User-Id") String userId);
}
