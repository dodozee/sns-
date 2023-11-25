package com.withsports.notificationservice.domain.team.dto.Producer;

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
