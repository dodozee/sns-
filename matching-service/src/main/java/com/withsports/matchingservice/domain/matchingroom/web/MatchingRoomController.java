package com.withsports.matchingservice.domain.matchingroom.web;


import com.withsports.matchingservice.domain.matchingroom.dto.MatchingRoomDto;
import com.withsports.matchingservice.domain.matchingroom.service.MatchingRoomService;
import com.withsports.matchingservice.domain.matchingroom.web.response.GetMatchingRoomResponse;
import com.withsports.matchingservice.domain.matchingroom.web.response.MatchingRoomResponse;
import com.withsports.matchingservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MatchingRoomController {

    private final MatchingRoomService matchingRoomService;

    //TODO : 매칭방 목록 조회 -
    // 매칭방 목록 조회시 방장이 아닌 유저는 매칭방 입장 버튼만 보여야함,
    // 매칭방 입장시 매칭방 입장 버튼은 매칭방 나가기 버튼으로 변경되어야함,
    // 방장은 매칭방 나가기 대신 삭제 버튼만 보여야함
    // <우선 이렇게하면 좋을거 같아서 위에 적어놓은 것들이니 꼭 안해도 됨.>
    @GetMapping("/matchingrooms")
    ResponseEntity<Result> getMatchingRoomList(@RequestHeader("user-id") String userId) {
        System.out.println("매칭방 목록 조회 api 호출");
        List<MatchingRoomDto> roomList = matchingRoomService.findMatchingRoomList(Long.valueOf(userId));
        List<MatchingRoomResponse> responses = roomList.stream()
                .map(MatchingRoomResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(responses));
    }


    //TODO : 매칭방 입장
    @PostMapping("/matchingroom/{matchingRoomId}")
    ResponseEntity<Result> enterMatchingRoom(@RequestHeader("user-id") String userId,
                                             @PathVariable("matchingRoomId") String matchingRoomId) throws Exception {
        System.out.println(userId + "매칭방 입장 api 호출");
        matchingRoomService.enterMatchingRoom(Long.valueOf(userId), Long.valueOf(matchingRoomId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("매칭방 입장 성공"));
    }

    //TODO : 매칭방 나가기
    @DeleteMapping("/matchingroom/{matchingRoomId}")
    ResponseEntity<Result> exitMatchingRoom(@RequestHeader("user-id") String userId,
                                            @PathVariable("matchingRoomId") String matchingRoomId) {
        System.out.println(userId + "매칭방 나가기 api 호출");
        matchingRoomService.exitMatchingRoom(Long.valueOf(userId), Long.valueOf(matchingRoomId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("매칭방 나가기 성공"));
    }


    @GetMapping("/feign/matchingroom/{matchingRoomId}")
    ResponseEntity<Result> getMatchingRoomByMatchingRoomId(@PathVariable("matchingRoomId") String matchingRoomId){
        System.out.println("feign 매칭방 조회 api 호출");
        MatchingRoomDto matchingRoomDto = matchingRoomService.findMatchingRoomById(Long.valueOf(matchingRoomId));
        GetMatchingRoomResponse response = new GetMatchingRoomResponse(matchingRoomDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }


}
