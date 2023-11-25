package com.withsports.notificationservice.domain.team.messagequeue.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceTeamDto;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceTeamUserDto;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceUpdateUserProfileDto;
import com.withsports.notificationservice.domain.team.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class UserNotificationConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;


//    @Transactional
//    @KafkaListener(topics = "updatedUserForNotification")
//    public void teamDeleted(String kafkaMessage) throws JsonProcessingException {
//        log.debug("## NotificationConsumer.updatedUserForNotification");
//        log.debug("#### kafka Message = {}", kafkaMessage);
//
//        KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto = objectMapper.readValue(kafkaMessage, KafkaProduceUpdateUserProfileDto.class);
//
//    }





}
