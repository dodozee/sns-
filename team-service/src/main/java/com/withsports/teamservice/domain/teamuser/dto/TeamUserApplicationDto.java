package com.withsports.teamservice.domain.teamuser.dto;

import com.withsports.teamservice.domain.teamuser.entity.Role;
import com.withsports.teamservice.domain.teamuser.entity.TeamUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserApplicationDto {

    private Long teamId;
    private Long userId;
    private String introduction;
    private Role role;
    private Long elapsedTime;


    @Builder
    public TeamUserApplicationDto(Long teamId, Long userId, String introduction) {
        this.teamId = teamId;
        this.userId = userId;
        this.introduction = introduction;
    }
    @Builder
    public TeamUserApplicationDto(Long teamId, Long userId, Role role) {
        this.teamId = teamId;
        this.userId = userId;
        this.role = role;
    }

    public TeamUserApplicationDto(TeamUser teamUser) {
        this.teamId = teamUser.getTeam().getId();
        this.userId = teamUser.getUserId();
        this.introduction = teamUser.getIntroduction();
        this.role = teamUser.getRole();
        this.elapsedTime = getElapsedTime(teamUser);
    }


    public static TeamUserApplicationDto of(Long teamId, Long userId, String introduction) {
        return TeamUserApplicationDto.builder()
                .teamId(teamId)
                .userId(userId)
                .introduction(introduction)
                .build();
    }

    private Long getElapsedTime(TeamUser teamUser) {
        Instant appliedAt = teamUser.getAppliedAt().toInstant(ZoneOffset.UTC);
        Instant now = Instant.now();

        Duration elapsedTime = Duration.between(appliedAt, now);

        return elapsedTime.getSeconds();
    }


}
