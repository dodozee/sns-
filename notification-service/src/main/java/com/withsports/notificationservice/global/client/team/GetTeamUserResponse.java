package com.withsports.notificationservice.global.client.team;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class GetTeamUserResponse {
    private Long id;
    private Long teamId;
    private Long userId;
}
