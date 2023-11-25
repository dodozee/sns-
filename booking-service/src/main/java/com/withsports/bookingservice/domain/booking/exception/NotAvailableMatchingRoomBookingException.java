package com.withsports.bookingservice.domain.booking.exception;

import com.withsports.bookingservice.global.exception.BookingException;
import org.springframework.http.HttpStatus;

public class NotAvailableMatchingRoomBookingException extends BookingException {
    public NotAvailableMatchingRoomBookingException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
