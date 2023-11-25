package com.withsports.notificationservice.domain.team.dto.Producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceCancelMatchingDto {
    private List<Long> userIds;
    private String title;
}
