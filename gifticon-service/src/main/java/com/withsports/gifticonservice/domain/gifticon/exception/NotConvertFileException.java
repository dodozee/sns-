package com.withsports.gifticonservice.domain.gifticon.exception;

import com.withsports.gifticonservice.domain.gifticon.entity.Gifticon;
import com.withsports.gifticonservice.global.exception.GifticonException;
import org.springframework.http.HttpStatus;

public class NotConvertFileException extends GifticonException {
    public NotConvertFileException(String message) {
        super(HttpStatus.NOT_ACCEPTABLE, message);
    }
}

