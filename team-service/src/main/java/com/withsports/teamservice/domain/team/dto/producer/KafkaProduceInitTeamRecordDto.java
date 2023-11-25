package com.withsports.teamservice.domain.team.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class KafkaProduceInitTeamRecordDto {
    private Long teamId;
    private String teamName;
    private String sports;

    public static KafkaProduceTeamDto of(Long teamId, String teamName) {
        return KafkaProduceTeamDto.builder()
                .teamId(teamId)
                .teamName(teamName)
                .build();
    }
}
