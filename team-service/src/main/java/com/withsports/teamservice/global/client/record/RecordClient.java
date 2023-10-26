package com.withsports.teamservice.global.client.record;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("RECORD-SERVICE")
public interface RecordClient {

    //팀의 역대 승/무/패 가져옴
    @GetMapping("/record/{teamId}")
    GetRecordResponse getRecordByTeamId(Long teamId);
}
