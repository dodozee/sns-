package com.withsports.matchingservice.global.client.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTeamRecordResponse {
    private Long teamId;
    private Long win;
    private Long draw;
    private Long lose;
    private Long score;
}
