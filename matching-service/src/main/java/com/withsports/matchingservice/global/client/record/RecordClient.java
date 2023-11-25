package com.withsports.matchingservice.global.client.record;

import com.withsports.matchingservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("RECORD-SERVICE")
public interface RecordClient {

    //팀의 역대 승/무/패 가져옴
    @GetMapping("/record/team/{teamId}")
    Result<GetTeamRecordResponse> getRecordByTeamId(@PathVariable("teamId") String teamId);

    //개인의 축구/풋살/농구 승/무/패 가져옴
    @GetMapping("/record/user/{userId}/sports/{sports}")
    Result<GetUserRecordResponse> getRecordByUserId(@PathVariable("userId") String userId,
                                                    @PathVariable("sports") String sports);
}
