package com.withsports.notificationservice.global.client.team;


import com.withsports.notificationservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("TEAM-SERVICE")
public interface TeamClient {

    @GetMapping("/team/feign/{teamId}")
    Result<GetTeamResponse> getTeam(@PathVariable(value = "teamId") String teamId);
    @GetMapping("/teamuser/feign/{teamId}/{userId}")
    Result<GetTeamUserResponse> getTeamUserFeign(@PathVariable(value = "teamId") String teamId,
                                                 @PathVariable(value = "userId") String userId);
    @GetMapping("/team/feign/{teamId}")
    Result<GetTeamUserListResponse> getTeamUserListFeign(@PathVariable(value = "teamId") String teamId);

}
