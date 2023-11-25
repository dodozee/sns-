package com.withsports.teamservice.domain.team.exception;

import com.withsports.teamservice.global.exception.TeamException;
import org.springframework.http.HttpStatus;

public class ExistSameTeamException extends TeamException {
    public ExistSameTeamException( String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
