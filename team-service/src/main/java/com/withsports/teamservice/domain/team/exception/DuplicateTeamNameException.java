package com.withsports.teamservice.domain.team.exception;

import com.withsports.teamservice.global.exception.TeamException;
import org.springframework.http.HttpStatus;

public class DuplicateTeamNameException extends TeamException {
        public DuplicateTeamNameException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
}
