package com.withsport.userservice.domain.user.exception;

import com.withsport.userservice.global.exception.UserException;
import org.springframework.http.HttpStatus;

public class DuplicateUserNicknameException extends UserException {

    public DuplicateUserNicknameException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
