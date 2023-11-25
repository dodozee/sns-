package com.withsports.pointservice.domain.point.exception;

import com.withsports.pointservice.global.exception.PointException;
import org.springframework.http.HttpStatus;

public class NotEnoughBalanceException extends PointException {
    public NotEnoughBalanceException( String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
