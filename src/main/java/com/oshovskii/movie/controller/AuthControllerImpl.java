package com.oshovskii.movie.controller;

import com.oshovskii.movie.controller.intefaces.AuthController;
import com.oshovskii.movie.dto.auth.AuthRequestDto;
import com.oshovskii.movie.dto.auth.AuthResponseDto;
import com.oshovskii.movie.dto.auth.SignUpRequestDto;
import com.oshovskii.movie.dto.users.UserDto;
import com.oshovskii.movie.model.User;
import com.oshovskii.movie.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthControllerImpl implements AuthController {
    private final AuthService authServiceImpl;
    private final ModelMapper modelMapper;

    @PostMapping("/signup")
    @Override
    public UserDto signUp(@RequestBody @Valid SignUpRequestDto signUpRequest) {

        log.info(String.format("[ signUp() ] Received SignUpRequestDto: %s", signUpRequest));

        User user = modelMapper.map(signUpRequest, User.class);
        User savedUser = authServiceImpl.saveNewUser(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @PostMapping("/recover")
    @Override
    public AuthResponseDto recoverUserId(@RequestBody @Valid AuthRequestDto request) {

        log.info(String.format("[ recoverUserId() ] Received AuthRequestDto: %s", request));

        User user = authServiceImpl.recoverUserId(request);
        return new AuthResponseDto(user.getId(), user.getPassword(), user.getUsername(), user.getName());
    }
}
