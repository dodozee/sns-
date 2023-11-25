package com.withsports.notificationservice.domain.team.web.response;

import com.withsports.notificationservice.domain.team.dto.FindNotificationDto;
import com.withsports.notificationservice.domain.team.dto.NotificationDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GetNotificationResponse {
    private List<NotificationDto> notifications;

    public GetNotificationResponse(List<FindNotificationDto> notifications){
        this.notifications = notifications
                .stream()
                .map(NotificationDto::new)
                .collect(Collectors.toList());
        ;
    }
}
