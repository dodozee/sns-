package com.withsports.gifticonservice.global.exception;

import com.withsports.gifticonservice.global.dto.Result;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GifticonException extends RuntimeException {
    private HttpStatus status;
    private Result errorResult;;


    protected GifticonException(HttpStatus status, String message){
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
