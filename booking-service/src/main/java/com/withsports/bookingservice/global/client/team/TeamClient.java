package com.withsports.bookingservice.global.client.team;

import com.withsports.bookingservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("TEAM-SERVICE")
public interface TeamClient {
    @GetMapping("/feign/team/leaderId/{leaderId}/sports/{sports}")
    Result<GetTeamIdResponse> getTeamByLeaderIdAndSportsId(@PathVariable("leaderId") String leaderId,
                                                           @PathVariable("sports") String sports);
}
