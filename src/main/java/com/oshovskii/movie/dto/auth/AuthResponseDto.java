package com.oshovskii.movie.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель сообщения авторизации с данными пользователя")
public class AuthResponseDto {
    @ApiModelProperty(notes = "Идентификатор пользователя", example = "1", required = true, position = 1)
    private Long userId;

    @ApiModelProperty(notes = "Пароль пользователя", example = "1231asascaW", required = true, position = 2)
    private String password;

    @ApiModelProperty(notes = "Никнейм пользователя", example = "username", required = true, position = 3)
    private String username;

    @ApiModelProperty(notes = "Имя пользователя", example = "Kate", required = true, position = 4)
    private String name;
}
