package com.oshovskii.movie.service;

import com.oshovskii.movie.model.Movie;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {
    Page<Movie> findAll(Integer page, int pageSize);
    List<Movie> findAllMoviesNotFavorite(String userId);
    List<Movie> findAllMoviesNotFavoriteCache(String userId);
    Long count();
    Movie save(Movie movie);
    Movie findById(Long movieId);
}
