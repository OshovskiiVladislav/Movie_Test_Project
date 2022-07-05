package com.oshovskii.movie.exceptions.implementations;

import com.oshovskii.movie.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

public class LoaderTypeException extends AbstractException {
    public LoaderTypeException(String message) {
        super(message);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
