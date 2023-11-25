package com.withsports.teamservice.domain.teamuser.dto.Producer;

import com.withsports.teamservice.domain.teamuser.entity.TeamUser;
import com.withsports.teamservice.domain.teamuser.entity.TeamUserStatus;
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

    public static KafkaProduceTeamUserDto of(TeamUser teamUser) {
        return KafkaProduceTeamUserDto.builder()
                .teamId(teamUser.getTeam().getId())
                .teamName(teamUser.getTeam().getTeamName())
                .leaderId(teamUser.getTeam().getLeaderId())
                .userId(teamUser.getUserId())
                .status(teamUser.getStatus())
                .build();
    }
}
