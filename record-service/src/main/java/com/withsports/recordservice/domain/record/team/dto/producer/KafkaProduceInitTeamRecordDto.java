package com.withsports.recordservice.domain.record.team.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class KafkaProduceInitTeamRecordDto {
    private Long teamId;
    private String teamName;
    private String sports;

}
