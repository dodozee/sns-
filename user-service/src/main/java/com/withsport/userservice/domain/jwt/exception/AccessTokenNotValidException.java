package com.withsport.userservice.domain.jwt.exception;

import com.withsport.userservice.global.exception.UserException;
import org.springframework.http.HttpStatus;

public class AccessTokenNotValidException extends UserException {

        public AccessTokenNotValidException(String message) {
            super(HttpStatus.UNAUTHORIZED, message);
        }

}
