package com.withsports.notificationservice.domain.team.messagequeue.team;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceTeamDto;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceTeamUserDto;
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
public class TeamNotificationConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;


    @Transactional
    @KafkaListener(topics = "teamDeleted")
    public void teamDeleted(String kafkaMessage) throws JsonProcessingException {
        log.debug("## NotificationConsumer.teamDeleted");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceTeamDto kafkaProduceTeamDto = objectMapper.readValue(kafkaMessage, KafkaProduceTeamDto.class);

        notificationService.deleteTeam(kafkaProduceTeamDto);
    }

    @Transactional
    @KafkaListener(topics = "teamUserPlaced")
    public void teamUserPlaced(String kafkaMessage) throws JsonProcessingException {
        log.debug("## NotificationConsumer.teamUserPlaced");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceTeamUserDto kafkaProduceTeamUserDto = objectMapper.readValue(kafkaMessage, KafkaProduceTeamUserDto.class);

        notificationService.placedJoinTeam(kafkaProduceTeamUserDto);
    }

    @Transactional
    @KafkaListener(topics = "teamUserAccepted")
    public void teamUserAccepted(String kafkaMessage) throws JsonProcessingException {
        log.debug("## NotificationConsumer.teamUserAccepted");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceTeamUserDto kafkaProduceTeamUserDto = objectMapper.readValue(kafkaMessage, KafkaProduceTeamUserDto.class);

        notificationService.acceptedJoinTeam(kafkaProduceTeamUserDto);
    }

    @Transactional
    @KafkaListener(topics = "teamUserRejected")
    public void teamUserRejected(String kafkaMessage) throws JsonProcessingException {
        log.debug("## NotificationConsumer.teamUserRejected");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceTeamUserDto kafkaProduceTeamUserDto = objectMapper.readValue(kafkaMessage, KafkaProduceTeamUserDto.class);

        notificationService.rejectedJoinTeam(kafkaProduceTeamUserDto);
    }






}
