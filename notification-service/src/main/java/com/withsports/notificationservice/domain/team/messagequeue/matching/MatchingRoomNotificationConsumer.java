package com.withsports.notificationservice.domain.team.messagequeue.matching;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceRoomFullDto;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceRoomUserListDto;
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
public class MatchingRoomNotificationConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;


    @Transactional
    @KafkaListener(topics = "matchingRoomDeleted")
    public void matchingRoomDeleted(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingRoomNotificationConsumer.matchingRoomDeleted");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceRoomUserListDto kafkaProduceRoomUserListDto = objectMapper.readValue(kafkaMessage, KafkaProduceRoomUserListDto.class);

        notificationService.deleteMatchingRoom(kafkaProduceRoomUserListDto);
    }



    @Transactional
    @KafkaListener(topics = "matchingRoomFulled")
    public void matchingRoomFulled(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingRoomNotificationConsumer.teamDeleted");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceRoomFullDto kafkaProduceRoomFullDto = objectMapper.readValue(kafkaMessage, KafkaProduceRoomFullDto.class);

        notificationService.fullMatchingRoom(kafkaProduceRoomFullDto);
    }



}
