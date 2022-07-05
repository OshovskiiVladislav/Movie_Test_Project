package com.oshovskii.movie.exceptions.implementations;

import com.oshovskii.movie.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

public class IncorrectAuthenticationDataException extends AbstractException  {
    public IncorrectAuthenticationDataException(String message) {
        super(message);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
