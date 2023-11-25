package com.withsports.matchingservice.domain.matching.web;


import com.withsports.matchingservice.domain.matching.dto.EndGameResultDto;
import com.withsports.matchingservice.domain.matching.dto.request.EndGameResultRequest;
import com.withsports.matchingservice.domain.matching.service.MatchingProducer;
import com.withsports.matchingservice.domain.matching.service.MatchingService;
import com.withsports.matchingservice.domain.matching.web.response.IsMatched;
import com.withsports.matchingservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class MatchingController {
    private final MatchingService matchingService;
    private final MatchingProducer matchingProducer;

    //TODO : 매칭 탐색 시작 - 매칭풀 등록
    @PostMapping("/matching/{matchingRoomId}/start")
    ResponseEntity<Result> startMatching(@PathVariable("matchingRoomId") String matchingRoomId,
                                         @RequestHeader("user-id") String roomLeaderId) throws Exception {
        System.out.println("매칭 시작 api 호출");

        //TODO : 매칭 시작시 매칭방의 인원이 처음 수용인원과 같은지 확인해야함
        //TODO : 승리시 몇점 패배시 몇점 비길시 몇점 이런식으로 점수를 보여줘야함.
        matchingProducer.startMatchingSearching(Long.valueOf(matchingRoomId), Long.valueOf(roomLeaderId));
//        matchingService.startMatching(Long.valueOf(matchingRoomId), Long.valueOf(roomLeaderId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("매칭 시작! 매칭 상대팀을 찾으면 알림 알려드립니다."));
    }

    //TODO : 매칭 탐색 취소 -> 매칭풀에서 제거, 매칭방 삭제, 알림 발생
    @PostMapping("/matching/{matchingRoomId}/cancel")
    ResponseEntity<Result> cancelMatching(@PathVariable("matchingRoomId") String matchingRoomId,
                                          @RequestHeader("user-id") String roomLeaderId) throws Exception {
        System.out.println("매칭 취소 api 호출");


        matchingService.cancelMatching(Long.valueOf(matchingRoomId), Long.valueOf(roomLeaderId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("매칭 취소 완료"));
    }
    //TODO : 경기 시작 - 경기 시작 알림 발생
    @PostMapping("/matching/{matchingRoomId}/startGame")
    ResponseEntity<Result> startGame(@PathVariable("matchingRoomId") String matchingRoomId,
                                          @RequestHeader("user-id") String roomLeaderId) throws Exception {
        System.out.println("경기 시작 api 호출");
        //TODO 경기가 잡혀있는지 확인 로직 -> 안잡혀있으면 Exception
        //TODO
        matchingService.startGame(Long.valueOf(matchingRoomId), Long.valueOf(roomLeaderId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("경기 시작"));
    }

    //TODO : 경기 종료 - 경기 결과(승/무/패,레이팅) 저장및 승리팀 점수 부여, 패배팀 점수 차감(record service), 참가자들 포인트 발생, 매칭방 상태변경, 알림 발생
    @PostMapping("/matching/{matchingRoomId}/endGame")
    ResponseEntity<Result> endGame(@PathVariable("matchingRoomId") String matchingRoomId,
                                   @RequestBody EndGameResultRequest endGameResultRequest,
                                   @RequestHeader("user-id") String roomLeaderId) throws Exception {
        EndGameResultDto resultDto = EndGameResultDto.of(endGameResultRequest, Long.valueOf(matchingRoomId), Long.valueOf(roomLeaderId));
        System.out.println("경기 종료 api 호출");

        //TODO
        matchingService.endGame(resultDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("경기 종료"));
    }

    //TODO 매칭이 잡힌 방인지 확인하는 api
    @GetMapping("/matching/{matchingRoomId}/isMatched")
    ResponseEntity<Result> isMatched(@PathVariable("matchingRoomId") String matchingRoomId,
                                     @RequestHeader("user-id") String roomLeaderId) throws Exception {
        System.out.println("매칭이 잡혔는지 확인하는 api 호출");
        //TODO
        boolean isMatched = matchingService.isMatched(Long.valueOf(matchingRoomId), Long.valueOf(roomLeaderId));
        IsMatched isMatchedResponse = new IsMatched(isMatched);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(isMatchedResponse));
    }


}

//
//@RestController
//@RequiredArgsConstructor
//public class MatchingController {
//    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
//    private final SseEmitter emitter;
//
//
//    //TODO : 매칭 시작 - 매칭풀 등록
//    @PostMapping("/matching/start/{matchingRoomId},  produces = MediaType.TEXT_EVENT_STREAM_VALUE")
//    public SseEmitter startMatching(@PathVariable("matchingRoomId") String matchingRoomId,
//                                         @RequestHeader("user-id") String roomLeaderId) {
//        boolean isMatchFound = false;
//        executorService.scheduleAtFixedRate(() -> {
//            try {
//
//                matchingService.startMatching(Long.valueOf(matchingRoomId), Long.valueOf(roomLeaderId));
//
//                if (isMatchFound) {
//
//                    emitter.send("매칭 완료");
//                    emitter.complete();
//                } else {
//
//                    emitter.send("매칭 중...");
//                }
//            } catch (Exception e) {
//                emitter.completeWithError(e);
//            }
//        }, 0, 10, TimeUnit.SECONDS);
//
//        return emitter;
//    }
//}
