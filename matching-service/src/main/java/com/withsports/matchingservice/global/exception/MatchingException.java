package com.withsports.matchingservice.global.exception;

import com.withsports.matchingservice.global.dto.Result;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MatchingException extends RuntimeException {
    private HttpStatus status;
    private Result errorResult;;


    protected MatchingException(HttpStatus status, String message){
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
