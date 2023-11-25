package com.withsports.bookingservice.domain.stadium.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class AvailableDateAndTimeAndCapacityDto {
    Long stadiumId;
    Integer year;
    Integer month;
    Integer day;
    Integer hour;
    Integer capacity;

    AvailableDateAndTimeAndCapacityDto(Long id, Integer year, Integer month, Integer day, Integer hour, Integer capacity){
        this.stadiumId = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.capacity = capacity;
    }
}
