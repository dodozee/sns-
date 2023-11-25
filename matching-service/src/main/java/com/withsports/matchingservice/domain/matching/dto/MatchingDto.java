package com.withsports.matchingservice.domain.matching.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class MatchingDto {
    private String sports;
    private String matchingRoomId;
    private Long sumRating;
    private Long capacity;
    private Long teamId;

    public MatchingDto(String matchingRoomId, String sports, Long sumRating, Long capacity, Long teamId) {
        this.sports = sports;
        this.matchingRoomId = matchingRoomId;
        this.sumRating = sumRating;
        this.capacity = capacity;
        this.teamId = teamId;
    }


}
