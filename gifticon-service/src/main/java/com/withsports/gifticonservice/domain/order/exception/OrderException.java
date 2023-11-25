package com.withsports.gifticonservice.domain.order.exception;

import com.withsports.gifticonservice.global.exception.GifticonException;
import org.springframework.http.HttpStatus;

public class OrderException extends GifticonException {
    public OrderException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
