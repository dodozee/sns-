package com.withsports.bookingservice.domain.stadium.web.response;

import com.withsports.bookingservice.domain.stadium.dto.StadiumDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StadiumResponse {
    private Long stadiumId; // 구장 Id
    private String stadiumName; // 구장 이름
    private String address; // 구장 주소
    private String detailAddress; // 구장 상세 주소
    private String phoneNumber; // 구장 전화번호
    private Long adminId; // 구장 등록한 관리자 Id

    public static StadiumResponse of(StadiumDto stadiumDto) {
        return StadiumResponse.builder()
                .stadiumId(stadiumDto.getStadiumId())
                .stadiumName(stadiumDto.getStadiumName())
                .address(stadiumDto.getAddress())
                .detailAddress(stadiumDto.getDetailAddress())
                .phoneNumber(stadiumDto.getPhoneNumber())
                .adminId(stadiumDto.getAdminId())
                .build();
    }
}
