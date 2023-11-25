package com.withsports.notificationservice.global.client.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class GetTeamUserListResponse {
    private Long id;
    private String teamName;
    private List<GetTeamUserResponse> teamUserList;
}
