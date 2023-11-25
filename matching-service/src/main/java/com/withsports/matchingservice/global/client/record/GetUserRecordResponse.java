package com.withsports.matchingservice.global.client.record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserRecordResponse {
    private Long userId;
    private String nickname;
    private String sports;
    private Long win;
    private Long draw;
    private Long lose;
    private Long rating;
    private String tier;
}
