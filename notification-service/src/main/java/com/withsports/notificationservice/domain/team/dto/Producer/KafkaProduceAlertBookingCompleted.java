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
public class KafkaProduceAlertBookingCompleted {
    private List<Long> matchingRoomUserIds1;
    private List<Long> matchingRoomUserIds2;
    private String matchingRoomName1;
    private String matchingRoomName2;
    private String description;

}
