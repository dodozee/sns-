package com.withsports.notificationservice.global.client.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GetTeamResponse {
    private Long id;
    private String teamName;
    private Long leaderId;

}
