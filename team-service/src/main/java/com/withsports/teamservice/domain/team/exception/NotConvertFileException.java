package com.withsports.teamservice.domain.team.exception;

import com.withsports.teamservice.global.exception.TeamException;
import org.springframework.http.HttpStatus;

public class NotConvertFileException extends TeamException {
    public NotConvertFileException(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }
}

