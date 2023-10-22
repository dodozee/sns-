package com.withsport.userservice.global.exception;

import com.withsport.userservice.global.dto.Result;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {
    private HttpStatus status;
    private Result errorResult;;


    protected UserException(HttpStatus status, String message){
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
