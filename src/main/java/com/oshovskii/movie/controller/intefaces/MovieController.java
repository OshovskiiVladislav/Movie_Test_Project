package com.oshovskii.movie.controller.intefaces;

import com.oshovskii.movie.dto.movies.MovieDto;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Api(value = "/api/v1/movies", tags = "Фильмы", produces = "application/json")
public interface MovieController {

    @ApiOperation(
            value = "Запрос всех фильмов",
            notes = "Endpoint возвращает список моделей фильмов",
            httpMethod = "GET"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "", response = MovieDto.class),
                    @ApiResponse(code = 404, message = "Movies not found")
            }
    )
    Page<MovieDto> findAllMovies(@ApiParam(value = "Номер страницы") Integer page,
                                 @ApiParam(value = "Количество элементов на странице") Integer size);

    @ApiOperation(
            value = "Запрос всех фильмов, которых нет в избранном пользователя",
            notes = "Endpoint возвращает список моделей фильмов",
            httpMethod = "GET"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "", response = MovieDto.class),
                    @ApiResponse(code = 404, message = "Movies not found")
            }
    )
    List<MovieDto> findAllMoviesNotFavorite(@ApiParam(value = "Параметр, равный sql или inMemory") String loaderType,
                                            @ApiParam(value = "Заголовок User-Id") String userId);
}
