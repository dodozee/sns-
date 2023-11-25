package com.withsports.teamservice.domain.team.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class GetTeamUserListResponse {
    private Long id;
    private String teamName;
    private List<GetTeamUserResponse> teamUserList;
}
