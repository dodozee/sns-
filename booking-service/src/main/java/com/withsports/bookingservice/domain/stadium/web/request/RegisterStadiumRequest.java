package com.withsports.bookingservice.domain.stadium.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterStadiumRequest {
    private String stadiumName;
    private String address;
    private String detailAddress;
    private String phoneNumber;
}
