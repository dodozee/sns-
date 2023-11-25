package com.withsports.bookingservice.domain.stadium.dto;

import com.withsports.bookingservice.domain.stadium.entity.Stadium;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StadiumDto {
    private Long stadiumId;
    private String stadiumName;
    private String address;
    private String detailAddress;
    private String phoneNumber;
    private Long adminId;

    public static StadiumDto of(Stadium stadium) {
        return StadiumDto.builder()
                .stadiumId(stadium.getId())
                .stadiumName(stadium.getStadiumName())
                .address(stadium.getAddress())
                .detailAddress(stadium.getDetailAddress())
                .phoneNumber(stadium.getPhoneNumber())
                .adminId(stadium.getAdminId())
                .build();
    }
}
