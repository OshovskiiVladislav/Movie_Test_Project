package com.oshovskii.movie.repositories;

import com.oshovskii.movie.model.Movie;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DisplayName("MovieRepository Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findAllMoviesNotInFavoritesByUser() +" +
            "with valid user id " +
            "should return List<Movie> test")
    void findAllMoviesNotInFavoritesByUser_validUserId_shouldReturnMovieList() {
        // Config
        val existingUserId = 4L;
        val existingMovie = new Movie(
                2L,
                "kinopoisk_id_test_2",
                "title_test_2",
                "https://st.kp.yandex.net/test2"
        );

        val targetMovieList = List.of(existingMovie);

        // Call
        val actualMovieList = movieRepository.findAllMoviesNotInFavoritesByUser(existingUserId);

        // Verify
        assertEquals(targetMovieList, actualMovieList);
    }
}
