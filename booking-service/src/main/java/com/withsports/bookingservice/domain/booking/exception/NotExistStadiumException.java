package com.withsports.bookingservice.domain.booking.exception;

import com.withsports.bookingservice.global.exception.BookingException;
import org.springframework.http.HttpStatus;

public class NotExistStadiumException extends BookingException {
    public NotExistStadiumException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
