package com.withsports.teamservice.domain.teamuser.exception;

import com.withsports.teamservice.global.exception.TeamException;
import org.springframework.http.HttpStatus;

public class NotTeamLeaderException extends TeamException {

    public NotTeamLeaderException( String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
