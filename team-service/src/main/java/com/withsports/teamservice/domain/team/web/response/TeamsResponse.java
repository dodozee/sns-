package com.withsports.teamservice.domain.team.web.response;

import com.withsports.teamservice.domain.team.dto.TeamDto;
import com.withsports.teamservice.domain.team.dto.TeamsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamsResponse {
    private Long teamId; // 팀 아이디
    private String teamName; // 팀 이름
    private String sports; // 종목
    private Long leaderId; // 팀장 아이디
    private String leaderName; // 팀장 이름
    private Long userCount; // 팀원 수

    public TeamsResponse(TeamsDto teamsDto) {
        this.teamId = teamsDto.getTeamId();
        this.teamName = teamsDto.getTeamName();
        this.sports = teamsDto.getSports();
        this.leaderId = teamsDto.getLeaderId();
        this.leaderName = teamsDto.getLeaderName();
        this.userCount = teamsDto.getUserCount();
    }

    public TeamsResponse(TeamDto teamDto) {
    }
}
