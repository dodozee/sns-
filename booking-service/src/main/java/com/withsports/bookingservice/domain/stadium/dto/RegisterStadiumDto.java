package com.withsports.bookingservice.domain.stadium.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterStadiumDto {
    private String stadiumName;
    private String address;
    private String detailAddress;
    private String phoneNumber;
    private Long adminId;

}
