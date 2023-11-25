package com.withsports.teamservice.domain.team.exception;

import com.withsports.teamservice.global.exception.TeamException;
import org.springframework.http.HttpStatus;

public class NotExistTeamException extends TeamException {

    public NotExistTeamException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
