package com.oshovskii.movie.dto.movies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Votes {
    @JsonProperty("_id")
    private String id;

    private Long imdb;

    private Long kp;

    private String await;

    private Integer russianFilmCritics;

    private Integer filmCritics;
}
