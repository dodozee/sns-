package com.withsports.matchingservice.domain.matchingroom.exception;

import com.withsports.matchingservice.global.exception.MatchingException;
import org.springframework.http.HttpStatus;

public class FullMatchingRoomException extends MatchingException {
    public FullMatchingRoomException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
