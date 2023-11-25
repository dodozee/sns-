package com.withsports.bookingservice.global.client.matching;


import com.withsports.bookingservice.global.client.user.GetUserResponse;
import com.withsports.bookingservice.global.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("MATCHING-SERVICE")
public interface MatchingClient {

    @GetMapping("/feign/matchingroom/{matchingRoomId}")
    Result<GetMatchingRoomResponse> getMatchingRoomByMatchingRoomId(@PathVariable("matchingRoomId") String matchingRoomId);

}
