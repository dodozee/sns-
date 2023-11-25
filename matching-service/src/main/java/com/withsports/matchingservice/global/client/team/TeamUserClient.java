package com.withsports.matchingservice.global.client.team;


import com.withsports.matchingservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("TEAM-SERVICE")
public interface TeamUserClient {
    @GetMapping("/teamuser/{userId}/sports/{sports}")
    Result<GetTeamResponse> getTeamIdByUserIdAndSports(@PathVariable("userId") String userId,
                                                       @PathVariable("sports") String sports);

    @GetMapping("/teamuser/feign/user/{userId}")
    Result<GetTeamIdsResponse> getTeamIdByUserId(@PathVariable("userId") String userId);

    @GetMapping("/team/feign/{teamId}")
    Result<GetTeamResponse> getTeam(@PathVariable("teamId") String teamId);
}
