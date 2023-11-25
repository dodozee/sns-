package com.withsports.bookingservice.domain.stadium.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailableTimeAndStadiumIdResponse {
    private Long stadiumId; // 구장 id
    private List<AvailableTimeResponse> availableTimeResponseList; // 구장 id,날짜에 해당하는 예약 가능 시간 리스트

    public static AvailableTimeAndStadiumIdResponse of(String stadiumId, List<AvailableTimeResponse> availableTimeDto) {
        return AvailableTimeAndStadiumIdResponse.builder()
                .stadiumId(Long.valueOf(stadiumId))
                .availableTimeResponseList(availableTimeDto)
                .build();
    }
}
