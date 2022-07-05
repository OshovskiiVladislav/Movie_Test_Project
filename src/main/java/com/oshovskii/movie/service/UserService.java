package com.oshovskii.movie.service;

import com.oshovskii.movie.dto.users.UpdateUserNameRequest;
import com.oshovskii.movie.dto.users.UpdateUserUsernameRequest;
import com.oshovskii.movie.model.Movie;
import com.oshovskii.movie.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    Optional<User> findByUsernameAndPassword(String username, String password);
    UserDetails loadUserByUsername(String username);
    User save(User user);
    User updateUsername(UpdateUserUsernameRequest request);
    User updateName(UpdateUserNameRequest request);
    void deleteById(Long userId);
    Movie addMovieToFavorite(Long movieId, String userId);
    void deleteMovieFromFavorite(Long movieId, String userId);
    Optional<User> findByIdFetch(Long userId);
}
