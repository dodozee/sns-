package com.withsport.userservice.global.client.record;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AverageUserRecordResponse {
    private Long userId;
    private String nickname;
    private String sports;
    private Long win;
    private Long lose;
    private Long draw;
    private Long rating;
    private String tier;
}