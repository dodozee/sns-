package com.withsport.userservice.global.client.team;

import com.withsport.userservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("TEAM-SERVICE")
public interface TeamClient {


    @PostMapping("/user/{userId}/profile")
    GetProfileUrlResponse uploadProfileUrl(@PathVariable("userId") String userId,
                                            @RequestPart MultipartFile image);

    @GetMapping("/teamuser/feign/user/{userId}")
    Result<GetTeamIdsResponse> getTeamByUserId(@PathVariable("userId") Long userId);

}
