package com.withsports.matchingservice.domain.matchingroom.web;

import com.withsports.matchingservice.domain.matchingroom.dto.CreateMatchingRoomDto;
import com.withsports.matchingservice.domain.matchingroom.service.MatchingRoomService;
import com.withsports.matchingservice.domain.matchingroom.web.request.CreateMatchingRoomRequest;
import com.withsports.matchingservice.global.dto.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MatchingRoomLeaderController {

    private final MatchingRoomService matchingRoomService;

    //TODO : 매칭방 생성 -방장도 생성하면 자동 입장되는 상태여야함
    @PostMapping("/matching/room")
    ResponseEntity<Result> createMatchingRoom(@Valid @RequestHeader("user-id") String userId,
                                              @RequestBody CreateMatchingRoomRequest createMatchingRoomRequest) {
         System.out.println("매칭방 생성 api 호출");
        CreateMatchingRoomDto request = CreateMatchingRoomDto.of(createMatchingRoomRequest);
        Long roomId = matchingRoomService.createMatchingRoom(Long.valueOf(userId), request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(roomId, "매칭방 생성 성공"));


    }


    //TODO : 매칭방 삭제
    @DeleteMapping("/matching/room/{matchingRoomId}")
    ResponseEntity<Result> deleteMatchingRoom(@PathVariable("matchingRoomId") String matchingRoomId,
                                              @RequestHeader("user-id") String roomLeaderId) throws Exception {
        System.out.println("매칭방 삭제 api 호출");
        matchingRoomService.deleteMatchingRoom(Long.valueOf(matchingRoomId), Long.valueOf(roomLeaderId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("매칭방 삭제 성공"));
    }




}
