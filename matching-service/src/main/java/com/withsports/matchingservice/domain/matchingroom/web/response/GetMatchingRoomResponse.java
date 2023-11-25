package com.withsports.matchingservice.domain.matchingroom.web.response;


import com.withsports.matchingservice.domain.matchingroom.dto.MatchingRoomDto;
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

    public GetMatchingRoomResponse(MatchingRoomDto matchingRoomDto) {
        this.matchingRoomId = matchingRoomDto.getMatchingRoomId();
        this.matchingRoomName = matchingRoomDto.getMatchingRoomName();
        this.matchingRoomLeaderId = matchingRoomDto.getRoomLeaderId();
        this.matchingRoomLeaderNickname = matchingRoomDto.getRoomLeaderNickname();
    }
}
