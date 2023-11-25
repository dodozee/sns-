package com.withsports.matchingservice.domain.matching.exception;

import com.withsports.matchingservice.global.exception.MatchingException;
import org.springframework.http.HttpStatus;

public class NotExactSportsException extends MatchingException {
    public NotExactSportsException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
