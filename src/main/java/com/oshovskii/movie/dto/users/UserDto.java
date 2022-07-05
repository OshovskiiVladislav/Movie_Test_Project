package com.oshovskii.movie.dto.users;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель пользователя")
public class UserDto {

    @ApiModelProperty(notes = "Идентификатор пользователя", example = "1", required = true, position = 1)
    private Long id;

    @ApiModelProperty(notes = "Никнейм пользователя", example = "username", required = true, position = 2)
    private String username;

    @ApiModelProperty(notes = "Пароль пользователя", example = "1231asascaW", required = true, position = 3)
    private String password;

    @ApiModelProperty(notes = "Почта пользователя", example = "qwerty@gmail.com", required = true, position = 4)
    private String email;
}
