package com.withsports.teamservice.domain.team.exception;

import com.withsports.teamservice.global.exception.TeamException;
import org.springframework.http.HttpStatus;

public class DuplicateTeamException extends TeamException {

        public DuplicateTeamException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
}
