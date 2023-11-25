package com.withsports.bookingservice.domain.stadium.web.response;


import com.withsports.bookingservice.domain.stadium.dto.AvailableDateAndTimeAndCapacityDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class AvailableDateAndTimeAndCapacityResponse {
    Long stadiumId; // 구장 Id
    Integer year;// 연도
    Integer month; // 1~12월 중 하나
    Integer day; // 1~31일 중 하나
    Integer hour; // 0~23시 중 하나
    Integer capacity; // 해당 시간의 예약 가능 인원 수

    AvailableDateAndTimeAndCapacityResponse(Long id, Integer year, Integer month, Integer day, Integer hour, Integer capacity){
        this.stadiumId = id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.capacity = capacity;
    }

    public static AvailableDateAndTimeAndCapacityResponse of(AvailableDateAndTimeAndCapacityDto availableDateAndTimeAndCapacityDto) {
        return AvailableDateAndTimeAndCapacityResponse.builder()
                .stadiumId(availableDateAndTimeAndCapacityDto.getStadiumId())
                .year(availableDateAndTimeAndCapacityDto.getYear())
                .month(availableDateAndTimeAndCapacityDto.getMonth())
                .day(availableDateAndTimeAndCapacityDto.getDay())
                .hour(availableDateAndTimeAndCapacityDto.getHour())
                .capacity(availableDateAndTimeAndCapacityDto.getCapacity())
                .build();
    }
}
