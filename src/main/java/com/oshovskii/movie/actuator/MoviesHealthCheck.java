package com.oshovskii.movie.actuator;

import com.oshovskii.movie.service.implementations.MovieServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MoviesHealthCheck implements HealthIndicator {

    private final MovieServiceImpl movieServiceImpl;

    @Override
    public Health health() {
        Long moviesCount = movieServiceImpl.count();
        return moviesCount == 0 ?
                Health.down().withDetail("LOG-COUNT-MOVIE", "No movies").build() :
                Health.up().withDetail("LOG-COUNT-MOVIE", "There " + moviesCount + " movies in the library!").build();
    }
}
