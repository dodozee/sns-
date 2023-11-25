package com.withsports.bookingservice.domain.booking.web;


import com.withsports.bookingservice.domain.booking.dto.*;
import com.withsports.bookingservice.domain.booking.service.BookingService;
import com.withsports.bookingservice.domain.booking.web.request.DateAndTimeRequest;
import com.withsports.bookingservice.domain.booking.web.request.DateRequest;
import com.withsports.bookingservice.domain.booking.web.response.CheckAvailableBookingRoomResponse;
import com.withsports.bookingservice.domain.booking.web.response.CompletedBookingDetailResponse;
import com.withsports.bookingservice.domain.stadium.web.response.AvailableTimeAndStadiumIdResponse;
import com.withsports.bookingservice.domain.stadium.web.response.AvailableTimeResponse;
import com.withsports.bookingservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;


    //TODO 예약 가능한 매칭방 조회(예약 단계의 방이 없거나 예약을 이미 했으면 조회시 안 나옴)
    @GetMapping("/booking/available")
    ResponseEntity<Result> getAvailableBooking(@RequestHeader("user-id") String userId){
        List<CheckAvailableBookingRoomDto> availableBooking = bookingService.getAvailableBooking(Long.valueOf(userId));

        List<CheckAvailableBookingRoomResponse> response = availableBooking.stream().map(CheckAvailableBookingRoomResponse::new).toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }

    //TODO 연도/월/일 입력 후 조회시 예약 가능한 시간을 응답
    @GetMapping("/booking/available/stadium/{stadiumId}/date")
    ResponseEntity<Result> getAvailableDate(@RequestBody DateRequest dateRequest,
                                            @PathVariable("stadiumId") String stadiumId){
        SearchingTimeByDateAndStadiumIdDto searchingTimeByDateAndStadiumIdDto = new SearchingTimeByDateAndStadiumIdDto(Long.valueOf(stadiumId),dateRequest);
        List<AvailableTimeDto> availableTimeDto = bookingService.getAvailableBookingDate(searchingTimeByDateAndStadiumIdDto);
        List<AvailableTimeResponse> list = availableTimeDto.stream()
                .map(AvailableTimeResponse::of)
                .toList();
        AvailableTimeAndStadiumIdResponse response = AvailableTimeAndStadiumIdResponse.of(stadiumId,list);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }

    //TODO 방리더 한명만 예약하면 양팀 다 자동으로 예약 됨
    @PostMapping("/booking/{matchingRoomId}")
    ResponseEntity<Result> booking(@RequestHeader("user-id") String userId,
                                   @PathVariable("matchingRoomId") String matchingRoomId,
                                   @RequestBody DateAndTimeRequest dateAndTimeRequest){
           DateAndTimeDto dateAndTimeDto = new DateAndTimeDto(dateAndTimeRequest);
           bookingService.booking(Long.valueOf(userId),Long.valueOf(matchingRoomId),dateAndTimeDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("예약 완료"));
    }

    //TODO  예약 일정 조회
    @GetMapping("/booking/schedule/team/{teamId}")
    ResponseEntity<Result> getBookingSchedule(@PathVariable("teamId") String teamId){
        List<CompletedBookingDetailDto> completedBookingDetailDtoList = bookingService.getBookingScheduleByTeamId(Long.valueOf(teamId));
        List<CompletedBookingDetailResponse> response = completedBookingDetailDtoList.stream()
                .map(CompletedBookingDetailResponse::of)
                .toList();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response,"예약 일정 조회"));
    }


}
