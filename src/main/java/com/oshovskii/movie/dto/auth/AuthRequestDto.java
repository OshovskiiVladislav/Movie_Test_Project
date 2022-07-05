package com.oshovskii.movie.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель сообщения авторизации")
public class AuthRequestDto {

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z]+$")
    @ApiModelProperty(notes = "Никнейм пользователя", example = "username", required = true, position = 1)
    private String username;

    @NotEmpty
    @ApiModelProperty(notes = "Пароль пользователя", example = "v3rystr0ngp4ssw0rd", required = true, position = 2)
    private String password;
}
