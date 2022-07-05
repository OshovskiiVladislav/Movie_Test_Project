package com.oshovskii.movie.controller.intefaces;

import com.oshovskii.movie.dto.auth.AuthRequestDto;
import com.oshovskii.movie.dto.auth.AuthResponseDto;
import com.oshovskii.movie.dto.auth.SignUpRequestDto;
import com.oshovskii.movie.dto.users.UserDto;
import io.swagger.annotations.*;

@Api(value = "/api/v1/auth", tags = "Регистрация и авторизация", produces = "application/json")
public interface AuthController {

    @ApiOperation(
            value = "Возвращает модель пользователя",
            notes = "Endpoint для регистрации нового пользователя",
            httpMethod = "POST"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "", response = UserDto.class),
                    @ApiResponse(code = 409, message = "User with email [xxx] already exists."),
                    @ApiResponse(code = 409, message = "User with username [xxx] already exists."),
            }
    )
    UserDto signUp(@ApiParam(value = "Модель запроса на регистрацию", required = true) SignUpRequestDto signUpRequest);

    @ApiOperation(
            value = "Возвращает AuthResponseDto",
            notes = "Endpoint для авторизации и получения AuthResponseDto зарегистрированным пользователем",
            httpMethod = "POST"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "", response = AuthResponseDto.class),
                    @ApiResponse(code = 401, message = "Incorrect AuthRequestDto provided.")
            }
    )
    AuthResponseDto recoverUserId(
            @ApiParam(value = "Модель запроса на авторизацию", required = true) AuthRequestDto request
    );
}
