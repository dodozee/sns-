package com.withsports.pointservice.domain.point.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointDto {
    private Long userId;
    private String nickname;
    private Long balance;
}
