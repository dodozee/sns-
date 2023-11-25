package com.withsports.teamservice.domain.team.dto;

import com.withsports.teamservice.domain.teamuser.entity.TeamUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamsDto {
    private Long teamId;
    private String teamName;
    private String sports;
    private Long leaderId;
    private String leaderName;
    private Long userCount;

    public TeamsDto(TeamUser teamUser) {
        this.teamId = teamUser.getTeam().getId();
        this.teamName = teamUser.getTeam().getTeamName();
        this.leaderName = teamUser.getTeam().getLeaderName();
        this.sports = teamUser.getTeam().getSports();
        this.leaderId = teamUser.getTeam().getLeaderId();
        this.userCount = (long) teamUser.getTeam().getTeamUsers().size();
    }


}
