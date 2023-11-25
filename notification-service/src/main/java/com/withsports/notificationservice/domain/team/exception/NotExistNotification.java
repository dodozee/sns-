package com.withsports.notificationservice.domain.team.exception;

import com.withsports.notificationservice.global.exception.NotificationException;
import org.springframework.http.HttpStatus;

public class NotExistNotification extends NotificationException {

    public NotExistNotification(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
