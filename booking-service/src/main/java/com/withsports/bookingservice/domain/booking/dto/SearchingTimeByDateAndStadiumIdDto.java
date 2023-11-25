package com.withsports.bookingservice.domain.booking.dto;

import com.withsports.bookingservice.domain.booking.web.request.DateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchingTimeByDateAndStadiumIdDto {
    private Integer year;
    private Integer month;
    private Integer day;
    private Long stadiumId;

    public SearchingTimeByDateAndStadiumIdDto(Long stadiumId, DateRequest dateRequest) {
        this.stadiumId = stadiumId;
        this.year = dateRequest.getYear();
        this.month = dateRequest.getMonth();
        this.day = dateRequest.getDay();
    }
}
