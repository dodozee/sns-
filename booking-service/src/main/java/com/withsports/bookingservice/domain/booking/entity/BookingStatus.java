package com.withsports.bookingservice.domain.booking.entity;


import lombok.Getter;

@Getter
public enum BookingStatus {
    DEFAULT("디폴트"),
    PLACED("예약대기"),
    COMPLETED("예약완료"),
    CANCELED("예약취소");

    private  String message;

    BookingStatus(String message){
        this.message = message;
    }
}
