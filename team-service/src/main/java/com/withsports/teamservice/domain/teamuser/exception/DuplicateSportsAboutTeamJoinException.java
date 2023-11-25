package com.withsports.teamservice.domain.teamuser.exception;

import com.withsports.teamservice.global.exception.TeamException;
import org.springframework.http.HttpStatus;

public class DuplicateSportsAboutTeamJoinException extends TeamException {
    protected DuplicateSportsAboutTeamJoinException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
