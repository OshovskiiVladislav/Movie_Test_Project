package com.oshovskii.movie.repositories;

import com.oshovskii.movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "SELECT * FROM movies m LEFT JOIN users_movies um ON (m.id = um.movie_id) WHERE um.user_id != :userId OR um.user_id IS NULL;", nativeQuery = true)
    List<Movie> findAllMoviesNotInFavoritesByUser(Long userId);
}
