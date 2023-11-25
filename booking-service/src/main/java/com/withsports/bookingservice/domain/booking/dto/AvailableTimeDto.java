package com.withsports.bookingservice.domain.booking.dto;

import com.withsports.bookingservice.domain.stadium.entity.AvailableTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableTimeDto {
    private Integer year; // 연도
    private Integer month; // 1~12월 중 하나
    private Integer day; // 1~31일 중 하나
    private Integer hour; // 0~23시 중 하나
    private Integer capacity; // 해당 시간의 예약 가능 인원 수
    private Integer bookingStatus; // 0: 예약 가능, 1: 예약 불가능

    public static AvailableTimeDto of(AvailableTime availableTime) {
        return AvailableTimeDto.builder()
                .year(availableTime.getAvailableDate().getYear())
                .month(availableTime.getAvailableDate().getMonth())
                .day(availableTime.getAvailableDate().getDay())
                .hour(availableTime.getHour())
                .capacity(availableTime.getCapacity())
                .bookingStatus(availableTime.getBookingStatus())
                .build();
    }
}
