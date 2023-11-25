package com.withsports.teamservice.domain.team.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class GetTeamUserResponse {
    private Long id;
    private Long teamId;
    private Long userId;
}
