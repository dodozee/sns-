package com.withsports.notificationservice.domain.team.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TeamUserDto {
    private Long id;
    private Long teamId;
    private Long userId;
}
