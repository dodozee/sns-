package com.withsports.recordservice.domain.record.user.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class KafkaProduceDrawRecordDto {
    private List<Long> userIds;
    private String sports;
    private Long matchingRoomId;
    private Long oneDrawTeamId;
    private String oneDrawTeamName;
    private Long twoDrawTeamId;
    private String twoDrawTeamName;
    private String result;
    private Long oneScore;
    private Long twoScore;
    private String area;
}
