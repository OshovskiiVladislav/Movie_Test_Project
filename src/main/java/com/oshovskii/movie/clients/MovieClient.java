package com.oshovskii.movie.clients;

import com.oshovskii.movie.dto.movies.KinopoiskMovieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "kinopoiskClient", url = "${kinopoisk.url.general}")
public interface MovieClient {
    @GetMapping("/movie")
    Optional<KinopoiskMovieDto> getMovies(@RequestParam("token") String token, @RequestParam("limit") String limit);
}
