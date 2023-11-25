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
public class KafkaProduceTeamDto {
    private Long teamId;
    private String teamName;
    private LocalDateTime createdAt;
    private List<Long> userIds;
    public static KafkaProduceTeamDto of(Long teamId, String teamName, List<Long> userIds) {
        return KafkaProduceTeamDto.builder()
                .teamId(teamId)
                .teamName(teamName)
                .userIds(userIds)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
