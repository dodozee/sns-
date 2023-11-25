package com.withsports.matchingservice.domain.matching.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaProduceEndGameDrawDto {
    private List<Long> userIds;
    private String drawTeamName1;
    private String drawTeamName2;
}
