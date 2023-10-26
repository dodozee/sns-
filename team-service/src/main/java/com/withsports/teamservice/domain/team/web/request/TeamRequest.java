package com.withsports.teamservice.domain.team.web.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRequest {
    @NotNull
    private String teamName; //팀 이름
    @NotNull
    private String teamLocation; //팀 위치
    private String teamImageUrl; //팀 로고
    private String introduction; //팀 소개글
    @NotNull
    private String sports; //종목

}


