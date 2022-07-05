package com.oshovskii.movie.security;

import com.oshovskii.movie.exceptions.implementations.IncorrectAuthenticationDataException;
import com.oshovskii.movie.model.Role;
import com.oshovskii.movie.model.User;
import com.oshovskii.movie.service.UserService;
import com.oshovskii.movie.service.implementations.UserServiceImpl;
import com.oshovskii.movie.utils.UserUtils;
import liquibase.pro.packaged.U;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserIdHeaderAuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String userIdAuth = httpServletRequest.getHeader("User-Id");

        log.info("[ UserIdHeaderAuthenticationFilter ] httpServletRequest.getHeader(\"User-Id\"): " +
                httpServletRequest.getHeader("User-Id"));

        if (userIdAuth != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = findByUserId(userIdAuth, httpServletResponse);

            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            null,
                            UserUtils.mapRolesToAuthorities(user.getRoles())
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private User findByUserId(String userIdHeader, HttpServletResponse httpServletResponse) throws IOException {

        Long userId = null;
        try {
            userId = Long.parseLong(userIdHeader);
        } catch (NumberFormatException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid User-Id header");
        }

        return userService.findByIdFetch(userId).orElseThrow(
                () -> new IncorrectAuthenticationDataException("User by User-Id header not found")) ;

    }
}
