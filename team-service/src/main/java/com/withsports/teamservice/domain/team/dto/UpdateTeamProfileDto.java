package com.withsports.teamservice.domain.team.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateTeamProfileDto {

    @NotNull
    private Long teamId;
    @NotNull
    private String teamName; //팀 이름
    @NotNull
    private String sports; //종목
    @NotNull
    private String area; //지역
    private String introduction; //팀 소개글



    @Builder
    public UpdateTeamProfileDto(Long id, String teamName, String introduction, String area, String sports) {
        this.teamId = id;
        this.teamName = teamName;
        this.introduction = introduction;
        this.area = area;
        this.sports = sports;
    }

}



