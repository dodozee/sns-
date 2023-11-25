package com.withsports.teamservice.domain.team.exception;

import com.withsports.teamservice.global.exception.TeamException;
import org.springframework.http.HttpStatus;

public class DuplicateTeamLeaderException extends TeamException {


        public DuplicateTeamLeaderException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
}


