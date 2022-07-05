package com.oshovskii.movie.dto.movies;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KinopoiskMovieDto {
    private Collection<Docs> docs;
    private Integer total;
    private Integer pages;
    private Integer limit;
    private Integer page;
}
