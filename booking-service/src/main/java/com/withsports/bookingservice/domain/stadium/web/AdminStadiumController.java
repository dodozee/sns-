package com.withsports.bookingservice.domain.stadium.web;


import com.withsports.bookingservice.domain.booking.dto.RegisterStadiumDateDto;
import com.withsports.bookingservice.domain.stadium.dto.StadiumDto;
import com.withsports.bookingservice.domain.stadium.service.StadiumService;
import com.withsports.bookingservice.domain.stadium.web.request.RegisterStadiumDateRequest;
import com.withsports.bookingservice.domain.stadium.web.request.RegisterStadiumRequest;
import com.withsports.bookingservice.domain.stadium.web.response.StadiumResponse;
import com.withsports.bookingservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminStadiumController {

    private final StadiumService stadiumService;

    //TODO 관리자- 예약 가능한 구장 등록(이름, 주소, 자세한 주소, 전화번호)
    @PostMapping("/admin/stadium")
    ResponseEntity<Result> registerStadium(@RequestBody RegisterStadiumRequest registerStadiumRequest,
                                           @RequestHeader("user-id") String adminId){
        stadiumService.registerStadium(registerStadiumRequest, adminId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.createSuccessResult("구장 등록 성공, 다음은 날짜와 시간을 등록해주세요"));
    }


    //TODO 관리자 - 구장별 예약 가능한 날짜&시간&수용인원 등록
    @PostMapping("/admin/stadium/{stadiumId}/date")
    ResponseEntity<Result> registerStadiumDate(@PathVariable("stadiumId") Long stadiumId,
                                               @RequestBody RegisterStadiumDateRequest registerStadiumDateRequest){
        RegisterStadiumDateDto registerStadiumDateDto = new RegisterStadiumDateDto(registerStadiumDateRequest);
        stadiumService.registerStadiumDate(stadiumId,registerStadiumDateDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.createSuccessResult("구장 날짜&시간 등록 성공"));
    }

    //TODO 관리자가 조회 가능한 구장 (지역 단위) ex) 서울특별시, 경기도, 인천광역시, 경상남도, 부산광역시 등
    @GetMapping("/admin/stadium/{area}")
    ResponseEntity<Result> getStadiums(@PathVariable("area") String area){
        List<StadiumDto> stadiumDtoList = stadiumService.findStadiumsByArea(area);
        List<StadiumResponse> response = stadiumDtoList.stream()
                .map(StadiumResponse::of)
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response,"구장 조회 성공"));
    }


}
