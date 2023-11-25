package com.withsports.bookingservice.domain.stadium.web.response;


import com.withsports.bookingservice.domain.booking.dto.AvailableTimeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableTimeResponse {
    private Integer year; // 연도
    private Integer month; // 1~12월 중 하나
    private Integer day; // 1~31일 중 하나
    private Integer hour; // 0~23시 중 하나
    private Integer capacity; // 해당 시간의 예약 가능 인원 수



    public static AvailableTimeResponse of(AvailableTimeDto availableTimeDto) {
        return AvailableTimeResponse.builder()
                .year(availableTimeDto.getYear())
                .month(availableTimeDto.getMonth())
                .day(availableTimeDto.getDay())
                .hour(availableTimeDto.getHour())
                .capacity(availableTimeDto.getCapacity())
                .build();
    }
}
