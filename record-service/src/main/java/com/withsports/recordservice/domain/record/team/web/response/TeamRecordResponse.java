package com.withsports.recordservice.domain.record.team.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRecordResponse {
    private Long id;
    private Long teamId;
    private String teamName;
    private String sports;
    private Long win;
    private Long lose;
    private Long draw;
    private Double winRate;
}
