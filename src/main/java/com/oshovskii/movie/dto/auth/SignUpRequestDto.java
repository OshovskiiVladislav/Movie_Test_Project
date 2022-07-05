package com.oshovskii.movie.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель сообщения решистрации")
public class SignUpRequestDto {

    @Email
    @NotEmpty
    @ApiModelProperty(notes = "Электронная почта", example = "user@mail.com", required = true, position = 1)
    private String email;

    @NotEmpty
    @ApiModelProperty(notes = "Пароль пользователя", example = "1231asascaW", required = true, position = 2)
    private String password;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z]+$")
    @ApiModelProperty(notes = "Никнейм пользователя", example = "username", required = true, position = 3)
    private String username;

    @ApiModelProperty(notes = "Имя пользователя", example = "Kate", required = true, position = 4)
    private String name;
}
