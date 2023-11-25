package com.withsports.notificationservice.domain.team.web;

import com.withsports.notificationservice.domain.team.dto.FindNotificationDto;
import com.withsports.notificationservice.domain.team.dto.UpdateNotificationDto;
import com.withsports.notificationservice.domain.team.service.NotificationService;
import com.withsports.notificationservice.domain.team.web.request.UpdateNotificationRequest;
import com.withsports.notificationservice.domain.team.web.response.GetNotificationResponse;
import com.withsports.notificationservice.global.dto.Result;
import com.withsports.notificationservice.global.dto.Yn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class TeamNotificationApi {

    private final NotificationService notificationService;



    //TODO 알림 조회
    @GetMapping("/notifications")
    public ResponseEntity<Result> getNotificationByUserId(@RequestHeader("user-id") String userIdHeader) {
        Long userId = Long.valueOf(userIdHeader);

        List<FindNotificationDto> notifications = notificationService.findNotificationByUserId(userId);

        GetNotificationResponse response = new GetNotificationResponse(notifications);
        return ResponseEntity.ok(Result.createSuccessResult(response));
    }

    @PutMapping("/notification/{notificationId}")
    public ResponseEntity<Result> updateNotification(@RequestHeader("user-id") String userIdHeader,
                                                     @PathVariable("notificationId") String notificationId,
                                                     @RequestBody UpdateNotificationRequest request) {

        Long userId = Long.valueOf(userIdHeader);
        UpdateNotificationDto updateDto = UpdateNotificationDto.of(Long.valueOf(notificationId), request.isRead());
        notificationService.updateNotification(updateDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("success"));
    }

    @GetMapping("/notification/counts")
    public ResponseEntity<Result> getNotificationCounts(@RequestHeader("user-id") String userIdHeader) {
        Long userId = Long.valueOf(userIdHeader);
        Yn readYn = Yn.N;
        Long counts = notificationService.findNotificationCounts(userId, readYn);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(counts));

    }
}
