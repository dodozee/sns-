package com.withsports.notificationservice.domain.team.dto;


import com.withsports.notificationservice.domain.team.entity.Notification;
import com.withsports.notificationservice.global.dto.Yn;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(staticName = "of")
public class FindNotificationDto {
    private Long id;//알림 아이디
    private Long userId;//유저 아이디
    private String message;//알림 메세지
    private String title;//알림 제목
    private Yn readYn;//읽음 여부
    private LocalDateTime createdAt;//알림 발생일 : 몇분전 이런거 사용 가능

    public FindNotificationDto(Notification entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.message = entity.getMessage();
        this.title = entity.getTitle();
        this.readYn = entity.getReadYn();
        this.createdAt = entity.getCreatedAt();
    }


}
