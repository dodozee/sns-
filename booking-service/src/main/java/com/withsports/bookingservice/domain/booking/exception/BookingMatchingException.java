package com.withsports.bookingservice.domain.booking.exception;

import com.withsports.bookingservice.global.exception.BookingException;
import org.springframework.http.HttpStatus;

public class BookingMatchingException extends BookingException {
    public BookingMatchingException( String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
