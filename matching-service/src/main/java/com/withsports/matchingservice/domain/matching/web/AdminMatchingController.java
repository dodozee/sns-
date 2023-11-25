package com.withsports.matchingservice.domain.matching.web;

import com.withsports.matchingservice.domain.matching.dto.MatchingRoomStatusDto;
import com.withsports.matchingservice.domain.matching.service.MatchingService;
import com.withsports.matchingservice.domain.matching.web.response.MatchingRoomStatusResponse;
import com.withsports.matchingservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminMatchingController {

    private final MatchingService matchingService;


    //TODO 현재 매칭풀수 확인
    @GetMapping("/matching/pool")
    ResponseEntity<Result> getMatchingPoolCount(){
        Long response =matchingService.getMatchingPoolCount();

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response,"매칭풀 수 확인 완료"));
    }

    //TODO 현재 매칭방 상태(예약전, 예약중, 경기중, 경기종료)의 갯수 조회
    @GetMapping("/matching/rooms/status")
    ResponseEntity<Result> getMatchingRoomsStatus(){

        MatchingRoomStatusDto matchingRoomsStatusDto = matchingService.getMatchingRoomsStatus();
        MatchingRoomStatusResponse matchingRoomStatusResponse = new MatchingRoomStatusResponse(matchingRoomsStatusDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(matchingRoomStatusResponse, "매칭풀 수 확인 완료"));
    }

}
