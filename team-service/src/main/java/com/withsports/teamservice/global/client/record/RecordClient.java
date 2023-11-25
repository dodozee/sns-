package com.withsports.teamservice.global.client.record;

import com.withsports.teamservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("RECORD-SERVICE")
public interface RecordClient {

    //팀의 역대 승/무/패 가져옴
    @GetMapping("/record/team/{teamId}")
    Result<TeamRecordResponse> getRecordByTeamId(@PathVariable("teamId") String teamId);

    @GetMapping("/feign/record/user/{userId}/sports/{sports}")
    Result<UserRecordResponse> getRecordByUserIdAndSports(@PathVariable("userId") String userId,
                                                          @PathVariable("sports") String sports);
}
