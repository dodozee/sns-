package com.withsports.teamservice.domain.team.dto;


import com.withsports.teamservice.domain.team.entity.Team;
import com.withsports.teamservice.global.entity.Sports;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamDto {
    private Long id;
    private Long leaderId;
    private String leaderName;
    private String teamName;
    private String introduction;
    private String area;
    private String sports;
    private String imageUrl;
    private Long teamMemberCount;


    @Builder
    public TeamDto(Long id, Long leaderId, String leaderName, String teamName, String introduction, String area, String sports, String imageUrl, Long teamMemberCount) {
        this.id = id;
        this.leaderId = leaderId;
        this.teamName = teamName;
        this.introduction = introduction;
        this.area = area;
        this.sports = sports;
        this.imageUrl = imageUrl;
        this.teamMemberCount = teamMemberCount;
    }

    @Builder
    public TeamDto(Long id, String teamName, String introduction, String area, String sports, String imageUrl) {
        this.id = id;
        this.teamName = teamName;
        this.introduction = introduction;
        this.area = area;
        this.sports = sports;
        this.imageUrl = imageUrl;
    }

    public TeamDto(Team team) {
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.leaderId = team.getLeaderId();
        this.leaderName = team.getLeaderName();
        this.introduction = team.getIntroduction();
        this.imageUrl = team.getImageUrl();
        this.area = team.getArea();
        this.sports = team.getSports();
        this.teamMemberCount = team.getTeamMemberCount();
    }

    public static TeamDto of(Team team, String imageUrl, Long teamMemberCount) {
        TeamDto teamDto = new TeamDto();
        teamDto.id = team.getId();
        teamDto.teamName = team.getTeamName();
        teamDto.leaderId = team.getLeaderId();
        teamDto.leaderName = team.getLeaderName();
        teamDto.introduction = team.getIntroduction();
        teamDto.area = team.getArea();
        teamDto.sports = team.getSports();
        teamDto.imageUrl = imageUrl ;
        teamDto.teamMemberCount = teamMemberCount;
        return teamDto;
    }
}
