package com.withsports.bookingservice.domain.booking.dto;

import com.withsports.bookingservice.domain.booking.web.request.DateAndTimeRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateAndTimeDto {
    private Long stadiumId;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer capacity;

    public DateAndTimeDto(DateAndTimeRequest dateAndTimeRequest) {
        this.stadiumId = dateAndTimeRequest.getStadiumId();
        this.year = dateAndTimeRequest.getYear();
        this.month = dateAndTimeRequest.getMonth();
        this.day = dateAndTimeRequest.getDay();
        this.hour = dateAndTimeRequest.getHour();
        this.capacity = dateAndTimeRequest.getCapacity();
    }
}
