package com.withsports.teamservice.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchTeamResult {
    private Long teamId;
    private String teamName;
    private String area;
    private String teamImageUrl;
    private Long win;
    private Long draw;
    private Long lose;
}
