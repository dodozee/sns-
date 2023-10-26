package com.withsports.teamservice.domain.team.dto;


import com.withsports.teamservice.domain.team.entity.Team;
import com.withsports.teamservice.global.entity.Sports;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateTeamDto {
    private String teamName;
    private Long leaderId;
    private String introduction;
    private String imageUrl;
    private String location;
    private String sports;


    @Builder
    public CreateTeamDto(String teamName, Long leaderId, String introduction, String imageUrl, String location, String sports){
        this.teamName = teamName;
        this.leaderId = leaderId;
        this.introduction = introduction;
        this.imageUrl = imageUrl;
        this.location = location;
        this.sports = sports;
    }
    public static CreateTeamDto of(Team team){
        CreateTeamDto createTeamDto = new CreateTeamDto();
        createTeamDto.teamName = team.getTeamName();
        createTeamDto.leaderId = team.getLeaderId();
        createTeamDto.introduction = team.getIntroduction();
        createTeamDto.imageUrl = team.getImageUrl();
        createTeamDto.location = team.getLocation();
        createTeamDto.sports = team.getSports();
        return createTeamDto;
    }

}
