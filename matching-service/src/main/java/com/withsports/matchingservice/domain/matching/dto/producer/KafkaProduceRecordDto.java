package com.withsports.matchingservice.domain.matching.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class KafkaProduceRecordDto {
    private List<Long> userIds;
    private String sports;
    private Long matchingRoomId;
    private Long winTeamId;
    private String winTeamName;
    private Long loseTeamId;
    private String loseTeamName;
    private String result;
    private Long winScore;
    private Long loseScore;
    private String area;
}
