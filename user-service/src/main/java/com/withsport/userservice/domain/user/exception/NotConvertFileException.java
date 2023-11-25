package com.withsport.userservice.domain.user.exception;

import com.withsport.userservice.global.exception.UserException;
import org.springframework.http.HttpStatus;

public class NotConvertFileException extends UserException {
    public NotConvertFileException(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }
}

