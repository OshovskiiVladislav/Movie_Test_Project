package com.oshovskii.movie.controller;

import com.oshovskii.movie.controller.intefaces.MovieController;
import com.oshovskii.movie.dto.movies.MovieDto;
import com.oshovskii.movie.exceptions.implementations.LoaderTypeException;
import com.oshovskii.movie.model.Movie;
import com.oshovskii.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieControllerImpl implements MovieController {
    private final MovieService movieService;
    private final ModelMapper modelMapper;
    private static final String LOADER_TYPE_SQL = "sql";
    private static final String LOADER_TYPE_CACHE = "inMemory";

    @GetMapping
    public Page<MovieDto> findAllMovies(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "s", defaultValue = "15") Integer size) {
        if (page < 1) {
            page = 1;
        }
        log.info(String.format("[ findAllMovies() ] Received page: %d and size: %d", page, size));
        return movieService.findAll(page, size).map(movie -> modelMapper.map(movie, MovieDto.class));
    }

    @GetMapping("/favorite/not")
    public List<MovieDto> findAllMoviesNotFavorite(@RequestParam(name = "loaderType") String loaderType,
                                                   @RequestHeader(name = "User-Id") String userId) {

        log.info(String.format("[ findAllMoviesNotFavorite() ] Received loaderType: %s " +
                "and User-Id: %s", loaderType, userId));

        List<Movie> movieList;
        if (LOADER_TYPE_SQL.equals(loaderType)) {
            movieList = movieService.findAllMoviesNotFavorite(userId);
        } else if (LOADER_TYPE_CACHE.equals(loaderType)) {
            movieList = movieService.findAllMoviesNotFavoriteCache(userId);
        } else {
            throw new LoaderTypeException("Request param [loaderType] is incorrect: " + loaderType);
        }
        return movieList
                .stream()
                .map(movie -> modelMapper.map(movie, MovieDto.class))
                .collect(Collectors.toList());
    }
}
