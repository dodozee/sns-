package com.withsports.bookingservice.global.client.matching;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMatchingRoomResponse {
    private Long matchingRoomId;
    private String matchingRoomName;
    private Long matchingRoomLeaderId;
    private String matchingRoomLeaderNickname;

}
