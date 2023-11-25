package com.withsports.matchingservice.domain.matchingroom.exception;

import com.withsports.matchingservice.global.exception.MatchingException;
import org.springframework.http.HttpStatus;

public class AlreadyParticipationException extends MatchingException {
    public AlreadyParticipationException( String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
