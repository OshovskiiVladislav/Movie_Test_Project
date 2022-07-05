package com.oshovskii.movie.dto.movies;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Модель сообщения фильма")
public class MovieDto {
    @ApiModelProperty(notes = "Идентификатор фильма", example = "1", required = true, position = 1)
    private Long id;

    @ApiModelProperty(notes = "Название фильма", example = "Мстители", required = true, position = 2)
    private String title;

    @ApiModelProperty(notes = "Ссылка на постер к фильму",
                      example = "https://st.kp.yandex.net/images/film_big/1095536.jpg", required = true, position = 1)
    private String posterPath;
}
