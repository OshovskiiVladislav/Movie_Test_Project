package com.oshovskii.movie.service;

import com.oshovskii.movie.dto.auth.AuthRequestDto;
import com.oshovskii.movie.model.User;

public interface AuthService {
    User recoverUserId(AuthRequestDto request);
    User saveNewUser(User user);
}
