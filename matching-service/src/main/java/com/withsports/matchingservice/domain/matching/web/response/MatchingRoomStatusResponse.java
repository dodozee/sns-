package com.withsports.matchingservice.domain.matching.web.response;

import com.withsports.matchingservice.domain.matching.dto.MatchingRoomStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingRoomStatusResponse {
    private Long beforeBooking; //예약전
    private Long doneBooking; //예약중
    private Long startGame; //경기중
    private Long endGame; //경기종료

    public MatchingRoomStatusResponse(MatchingRoomStatusDto matchingRoomsStatusDto) {
        this.beforeBooking = matchingRoomsStatusDto.getBeforeBooking();
        this.doneBooking = matchingRoomsStatusDto.getDoneBooking();
        this.startGame = matchingRoomsStatusDto.getStartGame();
        this.endGame = matchingRoomsStatusDto.getEndGame();
    }
}
