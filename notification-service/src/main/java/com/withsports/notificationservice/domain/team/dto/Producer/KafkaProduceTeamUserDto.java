package com.withsports.notificationservice.domain.team.dto.Producer;

import com.withsports.notificationservice.domain.team.messagequeue.team.TeamUserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class KafkaProduceTeamUserDto {
    private Long teamId;
    private String teamName;
    private Long leaderId;
    private Long userId;
    private TeamUserStatus status;

}
