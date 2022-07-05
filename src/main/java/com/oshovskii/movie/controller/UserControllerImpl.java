package com.oshovskii.movie.controller;

import com.oshovskii.movie.controller.intefaces.UserController;
import com.oshovskii.movie.dto.movies.MovieDto;
import com.oshovskii.movie.dto.users.UpdateUserNameRequest;
import com.oshovskii.movie.dto.users.UpdateUserUsernameRequest;
import com.oshovskii.movie.dto.users.UserDto;
import com.oshovskii.movie.model.Movie;
import com.oshovskii.movie.model.User;
import com.oshovskii.movie.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserControllerImpl implements UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping("/update/username")
    public UserDto updateUsername(@RequestBody UpdateUserUsernameRequest request) {
        log.info(String.format("[ updateUsername() ] Received UpdateUserUsernameRequest: %s", request));
        User updatedUser = userService.updateUsername(request);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @PostMapping("/update/name")
    public UserDto updateName(@RequestBody UpdateUserNameRequest request) {
        log.info(String.format("[ updateName() ] Received UpdateUserNameRequest: %s", request));
        User updatedUser = userService.updateName(request);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        log.info(String.format("[ deleteUser() ] Received user id: %d", userId));
        userService.deleteById(userId);
    }

    @PutMapping("/favorite/add/{movieId}")
    public MovieDto addMovieToFavorite(@PathVariable Long movieId, @RequestHeader(name = "User-Id") String userId) {
        log.info(String.format(
                "[ addMovieToFavorite() ] Received movie id: %d and header User-Id: %s", movieId, userId)
        );
        Movie movie = userService.addMovieToFavorite(movieId, userId);
        return modelMapper.map(movie, MovieDto.class);
    }

    @DeleteMapping("/favorite/del/{movieId}")
    public void deleteMovieFromFavorite(@PathVariable Long movieId, @RequestHeader(name = "User-Id") String userId) {
        log.info(String.format(
                "[ deleteMovieFromFavorite() ] Received movie id: %d and header User-Id: %s", movieId, userId)
        );
        userService.deleteMovieFromFavorite(movieId, userId);
    }
}
