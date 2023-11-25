package com.withsports.matchingservice.global.client.team;


import lombok.Data;

@Data
public class GetTeamResponse {
    private Long teamId;
    private String teamName;
    private String nickname;
}
