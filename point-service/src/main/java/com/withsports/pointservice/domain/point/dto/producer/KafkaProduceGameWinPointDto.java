package com.withsports.pointservice.domain.point.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaProduceGameWinPointDto {
    private List<Long> userIds;
    private Long matchingRoomId;
    private String teamName;
    private String opponentTeamName;

}
