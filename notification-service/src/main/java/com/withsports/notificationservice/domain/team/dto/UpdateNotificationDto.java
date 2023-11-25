package com.withsports.notificationservice.domain.team.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UpdateNotificationDto {
    private Long id;
    private boolean read;
}
