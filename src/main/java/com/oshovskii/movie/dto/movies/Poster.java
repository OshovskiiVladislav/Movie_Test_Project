package com.oshovskii.movie.dto.movies;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Poster {
    @JsonProperty("_id")
    private String id;

    private String previewUrl;

    private String url;
}
