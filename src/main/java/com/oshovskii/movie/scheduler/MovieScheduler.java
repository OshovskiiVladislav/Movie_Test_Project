package com.oshovskii.movie.scheduler;

import com.oshovskii.movie.clients.MovieClient;
import com.oshovskii.movie.dto.movies.KinopoiskMovieDto;
import com.oshovskii.movie.dto.movies.Docs;
import com.oshovskii.movie.dto.movies.Names;
import com.oshovskii.movie.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.movie.model.Movie;
import com.oshovskii.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovieScheduler {
    private final MovieClient movieClient;
    private final MovieService movieService;

    @Value("${kinopoisk.token}")
    private String token;

    @Value("${kinopoisk.limit}")
    private String limit;

    @Scheduled(fixedRateString = "PT3H")
    public void getNewMovies() {

        KinopoiskMovieDto kinopoiskMovieDto = movieClient.getMovies(token, limit)
                .orElseThrow(() -> new ResourceNotFoundException("KinopoiskMovieDto not found"));

        log.info("[ getNewMovies() ] Receiving KinopoiskMovieDto");

        ArrayList<Docs> docsList = (ArrayList<Docs>) kinopoiskMovieDto.getDocs();

        saveNewMovie(docsList);
    }

    /**
     * @param docsList
     * Save new Movie depending on name
     */
    private void saveNewMovie(ArrayList<Docs> docsList) {
        for (int i = 0; i < docsList.size(); i++) {
            Docs currentMovie = docsList.get(i);

            if(currentMovie.getName() != null && currentMovie.getPoster().getUrl() != null) {
                Movie movie = fillMovieEntity(
                        currentMovie.getExternalId().getId(),
                        currentMovie.getName(),
                        currentMovie.getPoster().getUrl()
                );
                movieService.save(movie);
            } else if (currentMovie.getAlternativeName() != null && currentMovie.getPoster().getUrl() != null) {
                Movie movie = fillMovieEntity(
                        currentMovie.getExternalId().getId(),
                        currentMovie.getAlternativeName(),
                        currentMovie.getPoster().getUrl()
                );
                movieService.save(movie);
            } else if (!currentMovie.getNames().isEmpty() && currentMovie.getPoster().getUrl() != null) {
                ArrayList<Names> namesArrayList = (ArrayList<Names>) currentMovie.getNames();
                Movie movie = fillMovieEntity(
                        currentMovie.getExternalId().getId(),
                        namesArrayList.get(0).getName(),
                        currentMovie.getPoster().getUrl()
                );
                movieService.save(movie);
            }
        }
    }

    private Movie fillMovieEntity(String kinopoiskId, String title, String posterPath) {
        return new Movie(
                null,
                kinopoiskId,
                title,
                posterPath
        );
    }
}
