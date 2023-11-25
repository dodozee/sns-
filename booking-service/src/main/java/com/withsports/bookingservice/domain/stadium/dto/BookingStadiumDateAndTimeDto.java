package com.withsports.bookingservice.domain.stadium.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingStadiumDateAndTimeDto {

    private Long stadiumId;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer capacity;

    BookingStadiumDateAndTimeDto (Long id, Integer year, Integer month, Integer day, Integer hour, Integer capacity){
        this.stadiumId = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.capacity = capacity;
    }


}
