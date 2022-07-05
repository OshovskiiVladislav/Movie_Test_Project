package com.oshovskii.movie.service.implementations;

import com.oshovskii.movie.exceptions.implementations.IncorrectInputDataException;
import com.oshovskii.movie.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.movie.model.Movie;
import com.oshovskii.movie.repositories.MovieRepository;
import com.oshovskii.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Movie> findAll(Integer page, int pageSize) {
        return movieRepository.findAll(PageRequest.of(page - 1, pageSize));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movie> findAllMoviesNotFavorite(String userId) {
        Long id;
        try {
            id = Long.valueOf(userId);
        } catch (NumberFormatException e) {
            throw new IncorrectInputDataException("Incorrect user id: " + userId);
        }
        return movieRepository.findAllMoviesNotInFavoritesByUser(id);
    }

    @Override
    @Cacheable("cacheList")
    @Transactional(readOnly = true)
    public List<Movie> findAllMoviesNotFavoriteCache(String userId) {
        Long id;
        try {
            id = Long.valueOf(userId);
        } catch (NumberFormatException e) {
            throw new IncorrectInputDataException("Incorrect user id: " + userId);
        }
        return movieRepository.findAllMoviesNotInFavoritesByUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long count() {
        return movieRepository.count();
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public Movie findById(Long movieId) {
        return movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Movie with id [ %d ] not found", movieId)));
    }
}
