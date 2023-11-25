package com.withsports.teamservice.domain.team.web.response;

import com.withsports.teamservice.domain.team.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamResponse {
    private Long id; //팀 아이디
    private Long leaderId; //팀장 아이디
    private String leaderName; //팀장 이름
    private String teamName; //팀 이름
    private String introduction; //팀 소개
    private String area; //지역
    private String sports; //종목
    private String imageUrl; //팀 이미지
    private Long teamMemberCount; //팀원 수

    public TeamResponse(TeamDto teamDto) {
        this.id = teamDto.getId();
        this.leaderId = teamDto.getLeaderId();
        this.leaderName = teamDto.getLeaderName();
        this.teamName = teamDto.getTeamName();
        this.introduction = teamDto.getIntroduction();
        this.area = teamDto.getArea();
        this.sports = teamDto.getSports();
        this.imageUrl = teamDto.getImageUrl();
        this.teamMemberCount = teamDto.getTeamMemberCount();
    }
}
