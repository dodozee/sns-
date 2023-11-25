package com.withsports.matchingservice.domain.matchingroom.exception;

import com.withsports.matchingservice.global.exception.MatchingException;
import org.springframework.http.HttpStatus;

public class NotExistMatchingRoomException extends MatchingException {

    public NotExistMatchingRoomException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
