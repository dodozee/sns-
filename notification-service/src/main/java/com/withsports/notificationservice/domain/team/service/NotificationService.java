package com.withsports.notificationservice.domain.team.service;

import com.withsports.notificationservice.domain.team.dto.FindNotificationDto;
import com.withsports.notificationservice.domain.team.dto.Producer.*;
import com.withsports.notificationservice.domain.team.dto.UpdateNotificationDto;
import com.withsports.notificationservice.global.dto.Yn;

import java.util.List;

public interface NotificationService {

    List<FindNotificationDto> findNotificationByUserId(Long id);

    void updateNotification(UpdateNotificationDto dto);

    Long findNotificationCounts(Long userId, Yn readYn);

    void acceptedJoinTeam(KafkaProduceTeamUserDto kafkaProduceTeamUserDto);

    void deleteTeam(KafkaProduceTeamDto kafkaProduceTeamDto);

    void placedJoinTeam(KafkaProduceTeamUserDto kafkaProduceTeamUserDto);

    void deleteMatchingRoom(KafkaProduceRoomUserListDto kafkaProduceRoomUserListDto);

    void fullMatchingRoom(KafkaProduceRoomFullDto kafkaProduceRoomFullDto);

    void successMatching(KafkaProduceMatchingDto kafkaProduceRoomUserListDto);

    void startGame(KafkaProduceStartGameDto kafkaProduceStartGameDto);

    void endGame(KafkaProduceEndGameDto kafkaProduceEndGameDto);

    void cancelMatching(KafkaProduceCancelMatchingDto kafkaProduceCancelMatchingDto);

    void endGameDraw(KafkaProduceEndGameDrawDto kafkaProduceEndGameDrawDto);

    void reportedUser(KafkaProduceReportDto kafkaProduceReportDto);

    void rejectedJoinTeam(KafkaProduceTeamUserDto kafkaProduceTeamUserDto);

    void notifyByOrderAccepted(KafkaProduceNotificationOrderAcceptedDto kafkaProduceNotificationOrderAcceptedDto);

    void notifyOrderRejected(KafkaProduceNotificationOrderRejectedDto kafkaProduceNotificationOrderRejectedDto);

    void alertBookingCompleted(KafkaProduceAlertBookingCompleted kafkaProduceAlertBookingCompleted);
}
