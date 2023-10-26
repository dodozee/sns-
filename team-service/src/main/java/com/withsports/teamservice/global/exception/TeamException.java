package com.withsports.teamservice.global.exception;

import com.withsports.teamservice.global.dto.Result;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TeamException extends RuntimeException {
    private HttpStatus status;
    private Result errorResult;;


    protected TeamException(HttpStatus status, String message){
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
