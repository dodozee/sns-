package com.withsports.bookingservice.domain.booking.dto;

import com.withsports.bookingservice.domain.stadium.web.request.RegisterStadiumDateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterStadiumDateDto {
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer capacity;

    public RegisterStadiumDateDto(RegisterStadiumDateRequest registerStadiumDateRequest) {
        this.year = registerStadiumDateRequest.getYear();
        this.month = registerStadiumDateRequest.getMonth();
        this.day = registerStadiumDateRequest.getDay();
        this.hour = registerStadiumDateRequest.getHour();
        this.capacity = registerStadiumDateRequest.getCapacity();
    }
}
