package com.withsports.teamservice.global.client.user;

import com.withsports.teamservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
public interface UserClient {
    @GetMapping("/user/{userId}")
    Result<GetUserResponse> getNicknameUserById(@PathVariable("userId") Long userId);


    @GetMapping("/user/nickname/feign/{userNickname}")
    Result<GetUserResponse> getUserByUserNickname(@PathVariable("userNickname") String userNickname);

}
