package com.withsport.userservice.global.client.record;

import com.withsport.userservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("RECORD-SERVICE")
public interface RecordClient {

    //팀의 역대 승/무/패 가져옴
    @GetMapping("/record/team/{teamId}")
    Result<TeamRecordResponse> getRecordByTeamId(@PathVariable("teamId") String teamId);

    @GetMapping("/record/user/average/{userId}")
    Result<AverageUserRecordResponse> getAverageRecordByUserId(@PathVariable("userId") String userId);

}
