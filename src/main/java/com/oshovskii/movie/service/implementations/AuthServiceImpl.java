package com.oshovskii.movie.service.implementations;

import com.oshovskii.movie.dto.auth.AuthRequestDto;
import com.oshovskii.movie.exceptions.implementations.IncorrectAuthenticationDataException;
import com.oshovskii.movie.exceptions.implementations.ResourceNotFoundException;
import com.oshovskii.movie.model.Role;
import com.oshovskii.movie.model.User;
import com.oshovskii.movie.service.AuthService;
import com.oshovskii.movie.service.RoleService;
import com.oshovskii.movie.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;

    private static final String DEFAULT_USER_ROLE = "ROLE_USER";

    @Override
    @Transactional
    public User recoverUserId(AuthRequestDto request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new IncorrectAuthenticationDataException("Incorrect username or password");
        }

        return userService.findByUsername(request.getUsername()).orElseThrow(() ->
                new ResourceNotFoundException(String.format("User with username %s not found", request.getUsername())));
    }

    @Override
    @Transactional
    public User saveNewUser(User user) {
        user.setId(null);
        user.setEmail(user.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername());

        Role role = roleService.getByName(DEFAULT_USER_ROLE);

        user.setRoles(Set.of(role));

        return userService.save(user);
    }
}
