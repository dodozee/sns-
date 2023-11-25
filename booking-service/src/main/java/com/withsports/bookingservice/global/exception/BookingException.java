package com.withsports.bookingservice.global.exception;

import com.withsports.bookingservice.global.dto.Result;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookingException extends RuntimeException {
    private HttpStatus status;
    private Result errorResult;;


    protected BookingException(HttpStatus status, String message){
        this.status = status;
        this.errorResult = Result.createErrorResult(message);
    }
}
