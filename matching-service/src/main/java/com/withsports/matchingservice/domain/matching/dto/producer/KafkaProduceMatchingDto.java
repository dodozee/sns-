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
public class KafkaProduceMatchingDto {
    private String myTeamName;
    private String yourTeamName;
    private List<Long> userIds;

}

