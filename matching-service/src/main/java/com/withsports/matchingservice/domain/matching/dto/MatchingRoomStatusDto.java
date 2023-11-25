package com.withsports.matchingservice.domain.matching.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingRoomStatusDto {
    private Long beforeBooking;
    private Long doneBooking;
    private Long startGame;
    private Long endGame;
}
