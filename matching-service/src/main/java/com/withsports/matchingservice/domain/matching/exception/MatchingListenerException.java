package com.withsports.matchingservice.domain.matching.exception;

import com.withsports.matchingservice.global.exception.MatchingException;
import org.springframework.http.HttpStatus;

public class MatchingListenerException extends MatchingException {
    public MatchingListenerException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
