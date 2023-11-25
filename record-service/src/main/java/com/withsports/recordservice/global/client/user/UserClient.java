package com.withsports.recordservice.global.client.user;

import com.withsports.recordservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
public interface UserClient {
    @GetMapping("/user/{userId}")
    Result<GetUserResponse> getNicknameUserById(@PathVariable("userId") Long userId);

}
