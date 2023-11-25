package com.withsports.teamservice.domain.team.web.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTeamProfileRequest {
    @NotNull
    private String teamName; //팀 이름
    @NotNull
    private String sports; //종목
    @NotNull
    private String area; //지역
    private String introduction; //팀 소개글
}
