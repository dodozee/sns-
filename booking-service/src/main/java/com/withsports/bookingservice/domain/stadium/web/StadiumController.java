package com.withsports.bookingservice.domain.stadium.web;

import com.withsports.bookingservice.domain.stadium.dto.AvailableDateAndTimeAndCapacityDto;
import com.withsports.bookingservice.domain.stadium.dto.StadiumDto;
import com.withsports.bookingservice.domain.stadium.service.StadiumService;
import com.withsports.bookingservice.domain.stadium.web.response.AvailableDateAndTimeAndCapacityResponse;
import com.withsports.bookingservice.domain.stadium.web.response.StadiumResponse;
import com.withsports.bookingservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StadiumController {

    private final StadiumService stadiumService;




    //TODO 사용자 - 지역별 경기장 이름, 위치(주소) 및 전화번호 조회
    @GetMapping("/stadium/{area}")
    ResponseEntity<Result> getStadiums(@PathVariable("area") String area){
        List<StadiumDto> stadiumDtoList = stadiumService.findStadiumsByArea(area);
        List<StadiumResponse> response = stadiumDtoList.stream()
                .map(StadiumResponse::of)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response,"구장 조회 성공"));
    }
    //TODO 사용자 - 경기장 id로 단건 조회
    @GetMapping("/stadium/one/{stadiumId}")
    ResponseEntity<Result> getStadium(@PathVariable("stadiumId") String stadiumId){
        StadiumDto stadiumDto = stadiumService.findStadiumById(Long.valueOf(stadiumId));
        StadiumResponse response = StadiumResponse.of(stadiumDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response,"구장 상세 조회 성공"));
    }

    //TODO 사용자 - 경기장 예약 가능 날짜&시간 조회(구장별)
    @GetMapping("/stadium/{stadiumId}/date")
    ResponseEntity<Result> getStadiumDate(@PathVariable("stadiumId") String stadiumId){
        List<AvailableDateAndTimeAndCapacityDto> stadiumDto = stadiumService.findStadiumDateById(Long.valueOf(stadiumId));
        List<AvailableDateAndTimeAndCapacityResponse> responses = stadiumDto.stream()
                .map(AvailableDateAndTimeAndCapacityResponse::of)
                .toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(responses,"구장 날짜&시간 조회 성공"));
    }

}
