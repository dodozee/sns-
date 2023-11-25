package com.withsports.matchingservice.global.client.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTeamIdResponse {
    private List<Long> teamIdList;
}