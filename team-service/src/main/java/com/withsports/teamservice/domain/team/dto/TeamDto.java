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
    private String teamName;
    private String introduction;
    private String area;
    private String sports;
    private String imageUrl;
    private Long teamMemberCount;


    @Builder
    public TeamDto(Long id, String teamName, String introduction, String area, String sports, String imageUrl, Long teamMemberCount) {
        this.id = id;
        this.teamName = teamName;
        this.introduction = introduction;
        this.area = area;
        this.sports = sports;
        this.imageUrl = imageUrl;
        this.teamMemberCount = teamMemberCount;
    }

    public static TeamDto of(Team team, Long teamMemberCount) {
        TeamDto teamDto = new TeamDto();
        teamDto.id = team.getId();
        teamDto.teamName = team.getTeamName();
        teamDto.introduction = team.getIntroduction();
        teamDto.area = team.getArea();
        teamDto.sports = team.getSports();
        teamDto.imageUrl = team.getImageUrl();
        teamDto.teamMemberCount = teamMemberCount;
        return teamDto;
    }
}
