package com.withsport.userservice.global.client.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
