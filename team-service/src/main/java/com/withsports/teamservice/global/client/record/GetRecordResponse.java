package com.withsports.teamservice.global.client.record;

import lombok.Data;

@Data
public class GetRecordResponse {
    private Long teamId;
    private Long win;
    private Long draw;
    private Long lose;
}
