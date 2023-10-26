package com.withsport.userservice.domain.user.exception;

import com.withsport.userservice.global.exception.UserException;
import org.springframework.http.HttpStatus;

public class NotExistUserException extends UserException {



    public NotExistUserException(String message){
        super(HttpStatus.BAD_REQUEST, message);
    }
}
