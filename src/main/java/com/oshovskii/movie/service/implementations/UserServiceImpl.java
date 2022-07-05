package com.oshovskii.movie.service.implementations;

import com.oshovskii.movie.dto.users.UpdateUserNameRequest;
import com.oshovskii.movie.dto.users.UpdateUserUsernameRequest;
import com.oshovskii.movie.exceptions.implementations.IncorrectInputDataException;
import com.oshovskii.movie.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.movie.model.Movie;
import com.oshovskii.movie.model.User;
import com.oshovskii.movie.repositories.UserRepository;
import com.oshovskii.movie.service.MovieService;
import com.oshovskii.movie.service.UserService;
import com.oshovskii.movie.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final MovieService movieService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                UserUtils.mapRolesToAuthorities(user.getRoles())
        );
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    @Transactional
    public User updateUsername(UpdateUserUsernameRequest request) {
        User toUpdateUser = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(String.format(
                        "User with email ['%s'] not found",
                        request.getEmail()))
        );
        toUpdateUser.setUsername(request.getUsername());
        return userRepository.save(toUpdateUser);
    }

    @Override
    @Transactional
    public User updateName(UpdateUserNameRequest request) {
        User toUpdateUser = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException(String.format(
                        "User with email ['%s'] not found",
                        request.getEmail()))
        );
        toUpdateUser.setName(request.getName());
        return userRepository.save(toUpdateUser);
    }

    @Transactional
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public Movie addMovieToFavorite(Long movieId, String userId) {
        Long id = valueOfUserId(userId);

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with id [ %d ] not found", id))
        );

        Movie movie = movieService.findById(movieId);

        user.getFavorites().add(movie);
        userRepository.save(user);

        return movie;
    }

    @Override
    @Transactional
    public void deleteMovieFromFavorite(Long movieId, String userId) {
        Long id = valueOfUserId(userId);

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("User with id [ %d ] not found", id))
        );

        Movie movie = movieService.findById(movieId);

        user.getFavorites().remove(movie);
        userRepository.save(user);
    }

    public Optional<User> findByIdFetch(Long userId) {
        return userRepository.findByIdFetch(userId);
    }

    private Long valueOfUserId(String userId) {
        Long id;
        try {
            id = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new IncorrectInputDataException("Incorrect input data");
        }
        return id;
    }
}
