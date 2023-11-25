package com.withsports.bookingservice.domain.stadium.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterStadiumDateRequest {
    private Integer year; // 2023년
    private Integer month; // 1~12월 중 하나
    private Integer day; // 1~31일 중 하나
    private Integer hour; // 0~23시 중 하나
    private Integer capacity; // 해당 시간의 예약 가능 인원 수
}
