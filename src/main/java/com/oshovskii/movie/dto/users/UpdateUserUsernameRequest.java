package com.oshovskii.movie.dto.users;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель сообщения изменения никнейма")
public class UpdateUserUsernameRequest {

    @ApiModelProperty(notes = "Новый ник пользователя", example = "world", required = true, position = 1)
    private String username;

    @Email
    @ApiModelProperty(notes = "Почта пользователя", example = "qwerty@gmail.com", required = true, position = 2)
    private String email;
}
