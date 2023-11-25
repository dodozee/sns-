package com.withsports.recordservice.global.exception;

import com.withsports.recordservice.global.dto.Result;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RecordException extends RuntimeException {
    private HttpStatus status;
    private Result errorResult;;


    protected RecordException(HttpStatus status, String message){
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
