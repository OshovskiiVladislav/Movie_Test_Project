package com.oshovskii.movie.dto.movies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rating {
    @JsonProperty("_id")
    private String id;

    private Double imdb;

    private Double kp;

    private Integer await;

    private Integer russianFilmCritics;

    private Integer filmCritics;
}
