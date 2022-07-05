package com.oshovskii.movie.service.implementations;

import com.oshovskii.movie.repositories.MovieRepository;
import com.oshovskii.movie.service.MovieService;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.oshovskii.movie.factory.TestEntityFactory.createMovie;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(MovieServiceImpl.class)
@DisplayName("MovieService test")
class MovieServiceImplTest {

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepositoryMock;

    @Test
    @DisplayName("findAll() " +
            "with valid page and size " +
            "should return Page<Movie> test")
    void findAll_validPageAndSize_shouldReturnPageMovies() {
        // Config
        val sourcePage = 1;
        val sourceSize = 2;
        val pageable = PageRequest.of(sourcePage - 1, sourceSize);

        val sourceMovie1 = createMovie(1);
        val sourceMovie2 = createMovie(2);

        val content = List.of(sourceMovie1, sourceMovie2);
        val targetPageMovies =  new PageImpl<>(content, pageable, content.size());

        when(movieRepositoryMock.findAll(pageable)).thenReturn(targetPageMovies);

        // Call
        val actual = movieService.findAll(sourcePage, sourceSize);

        // Verify
        assertEquals(targetPageMovies, actual);

        verify(movieRepositoryMock).findAll(pageable);
    }

    @Test
    @DisplayName("findAllMoviesNotFavorite() " +
            "with valid user id " +
            "should return Page<Movie> test")
    void findAllMoviesNotFavorite_validUserId_shouldReturnListMovies() {
        // Config
        String sourceUserId = "1";
        Long targetUserId = 1L;
        val targetMovie1 = createMovie(1);
        val targetMovie2 = createMovie(2);

        val targetMovieList = List.of(targetMovie1, targetMovie2);

        when(movieRepositoryMock.findAllMoviesNotInFavoritesByUser(targetUserId)).thenReturn(targetMovieList);

        // Call
        val actualListMovies = movieService.findAllMoviesNotFavorite(sourceUserId);

        // Verify
        assertEquals(targetMovieList, actualListMovies);

        verify(movieRepositoryMock).findAllMoviesNotInFavoritesByUser(targetUserId);
    }

    @Test
    @DisplayName("findAllMoviesNotFavoriteCache() " +
            "with valid user id " +
            "should return Page<Movie> test")
    void findAllMoviesNotFavoriteCache_validUserId_shouldReturnListMovies() {
        // Config
        String sourceUserId = "1";
        Long targetUserId = 1L;
        val targetMovie1 = createMovie(1);
        val targetMovie2 = createMovie(2);

        val targetMovieList = List.of(targetMovie1, targetMovie2);

        when(movieRepositoryMock.findAllMoviesNotInFavoritesByUser(targetUserId)).thenReturn(targetMovieList);

        // Call
        val actualListMovies = movieService.findAllMoviesNotFavoriteCache(sourceUserId);

        // Verify
        assertEquals(targetMovieList, actualListMovies);

        verify(movieRepositoryMock).findAllMoviesNotInFavoritesByUser(targetUserId);
    }

    @Test
    @DisplayName("count() " +
            "with void input " +
            "should return count movies test")
    void count_voidInput_shouldReturnCountMovies() {
        // Config
        val targetCount = 5L;

        when(movieRepositoryMock.count()).thenReturn(targetCount);

        // Call
        val actualCount = movieService.count();

        // Verify
        assertEquals(targetCount, actualCount);

        verify(movieRepositoryMock).count();
    }
}
