package com.withsports.recordservice.domain.record.user.web;

import com.withsports.recordservice.domain.record.user.dto.UserRankingByRatingDto;
import com.withsports.recordservice.domain.record.user.dto.UserRecordDetailHistory;
import com.withsports.recordservice.domain.record.user.dto.UserRecordDetailHistoryDto;
import com.withsports.recordservice.domain.record.user.dto.UserRecordDto;
import com.withsports.recordservice.domain.record.user.service.UserRecordService;
import com.withsports.recordservice.domain.record.user.web.response.AverageUserRecordResponse;
import com.withsports.recordservice.domain.record.user.web.response.GetUserRecordResponse;
import com.withsports.recordservice.domain.record.user.web.response.UserRecordDetailHistoryResponse;
import com.withsports.recordservice.domain.record.user.web.response.UserRecordResponse;
import com.withsports.recordservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRecordController {

    private final UserRecordService userRecordService;

    //TODO 사용자의 스포츠별 역대 기록 조회
    @GetMapping("/record/user/{userId}/sports/{sports}")
    ResponseEntity<Result> getRecordByUserId(@PathVariable("userId") String userId,
                                             @PathVariable("sports") String sports) {

        UserRecordDto userRecordDto = userRecordService.getRecordByUserId(Long.valueOf(userId), sports);
        GetUserRecordResponse getUserRecordResponse = GetUserRecordResponse.builder()
                .userId(userRecordDto.getUserId())
                .nickname(userRecordDto.getNickname())
                .sports(userRecordDto.getSports())
                .win(userRecordDto.getWin())
                .draw(userRecordDto.getDraw())
                .lose(userRecordDto.getLose())
                .rating(userRecordDto.getRating())
                .tier(userRecordDto.getTier())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(getUserRecordResponse));
    }

    @GetMapping("/record/user/average/{userId}")
    ResponseEntity<Result> getUserRecordByUserId(@PathVariable("userId") String userId) {
        List<UserRecordDto> userRecordDto = userRecordService.getAllRecordByUserId(Long.valueOf(userId));
        AverageUserRecordResponse average = new AverageUserRecordResponse(userRecordDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(average));
    }


    //TODO 사용자의 본인이 참여한 경기 리스트 전체 조회
    @GetMapping("/record/history/my/user")
    ResponseEntity<Result> getUserRecordHistoryByUserId(@RequestHeader("user-Id") String userId,
                                                        @PageableDefault Pageable pageable) {
        Page<UserRecordDetailHistoryDto> userRecordDetailHistoryDto = userRecordService.getPointDetailHistory(userId, pageable);
        System.out.println("userRecordDetailHistoryDto = " + userRecordDetailHistoryDto);
        List<UserRecordDetailHistory> userRecordDetailHistory = userRecordDetailHistoryDto.stream()
                .map(UserRecordDetailHistory::of)
                .toList();
        System.out.println("userRecordDetailHistory = " + userRecordDetailHistory);
        UserRecordDetailHistoryResponse pointDetailHistoryResponse = new UserRecordDetailHistoryResponse(
                userRecordDetailHistory,
                userRecordDetailHistoryDto.getNumber(),
                userRecordDetailHistoryDto.getTotalPages()
        );


        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(pointDetailHistoryResponse));
    }

    //TODO 다른 사용자의 참여한 경기 리스트 전체 조회
    @GetMapping("/record/history/other/user/{userId}")
    ResponseEntity<Result> getUserRecordHistoryByOtherUserId(@RequestHeader("user-Id") String userId) {


        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(null));
    }

    //TODO 사용자의 참여한 경기 스포츠별로 조회
    @GetMapping("/record/my/user/sports/{sports}")
    ResponseEntity<Result> getUserRecordHistoryByUserId(@RequestHeader("user-Id") String userId,
                                                        @PathVariable("sports") String sports) {


        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(null));
    }

    //TODO 사용자 레이팅 별 랭킹 조회(우선 보류)
    @GetMapping("/record/rating/rank")
    ResponseEntity<Result> getUserRecordRankByRating(@RequestHeader("user-Id") String userId) {
//        UserRankingByRatingDto userRankingByRatingDto = userRecordService.getUserRecordRankByRating(Long.valueOf(userId));
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(null));
    }


    @GetMapping("/feign/record/user/{userId}/sports/{sports}")
    ResponseEntity<Result> getRecordByUserIdAndSports(@PathVariable("userId") String userId,
                                                      @PathVariable("sports") String sports){
        UserRecordDto userRecordDto = userRecordService.getRecordByUserId(Long.valueOf(userId), sports);
        UserRecordResponse userRecordResponse = UserRecordResponse.builder()
                .userId(userRecordDto.getUserId())
                .nickname(userRecordDto.getNickname())
                .sports(userRecordDto.getSports())
                .win(userRecordDto.getWin())
                .draw(userRecordDto.getDraw())
                .lose(userRecordDto.getLose())
                .rating(userRecordDto.getRating())
                .tier(userRecordDto.getTier())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(userRecordResponse));
    }


}
