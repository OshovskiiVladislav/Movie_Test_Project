package com.oshovskii.movie.scheduler;

import com.oshovskii.movie.clients.MovieClient;
import com.oshovskii.movie.dto.movies.*;
import com.oshovskii.movie.model.Movie;
import com.oshovskii.movie.service.MovieService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(MovieScheduler.class)
@TestPropertySource(properties = { "kinopoisk.token = TEST-AAA-BBB-CCC", "kinopoisk.limit = 50" })
@DisplayName("UserScheduler test")
class MovieSchedulerTest {

    @Autowired
    private MovieScheduler movieScheduler;

    @MockBean
    private MovieService movieServiceMock;

    @MockBean
    private MovieClient movieClientMock;

    @Test
    @DisplayName("getNewMovies() " +
            "with void input" +
            "should save new movies test")
    void getNewMovies_voidInput_shouldSaveNewMovies() {
        // Config
        val token = "TEST-AAA-BBB-CCC";
        val limit = "50";

        val targetPoster = new Poster("1", "preview_url_test", "url_test");
        val targetDocs = new Docs(
                "1",
                "2021",
                new Rating(),
                new ExternalId("id", "imdb"),
                "description",
                "short_description",
                "type",
                "movieLength",
                new ArrayList<>(),
                "name",
                "en_name",
                new Votes(),
                "alternative_name_test",
                targetPoster
        );

        ArrayList<Docs> docsArrayList = new ArrayList<>();
        docsArrayList.add(targetDocs);

        val targetKinopoiskMovieDto = new KinopoiskMovieDto(
                docsArrayList,
                1,
                1,
                1,
                1
        );
      //  targetKinopoiskMovieDto.setDocs(Collections.singleton(targetDocs));

        when(movieClientMock.getMovies(token, limit)).thenReturn(Optional.of(targetKinopoiskMovieDto));

        when(movieServiceMock.save(any(Movie.class))).thenReturn(new Movie());

        // Call
        movieScheduler.getNewMovies();

        // Verify
        verify(movieClientMock).getMovies(token, limit);
        verify(movieServiceMock).save(any(Movie.class));
    }
}
