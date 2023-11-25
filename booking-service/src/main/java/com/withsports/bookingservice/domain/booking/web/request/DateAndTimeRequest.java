package com.withsports.bookingservice.domain.booking.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateAndTimeRequest {
    private Long stadiumId; // 구장 id
    private Integer year; // 연도
    private Integer month; // 1~12월 중 하나
    private Integer day; // 1~31일 중 하나
    private Integer hour; // 0~23시 중 하나
    private Integer capacity; // 해당 시간의 예약하려는 총 인원 수(양쪽 팀다 합친 수)

}
