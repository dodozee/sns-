package com.withsports.notificationservice.domain.team.messagequeue.matching;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.notificationservice.domain.team.dto.Producer.*;
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
public class MatchingNotificationConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;


    @Transactional
    @KafkaListener(topics = "matchingSucceeded")
    public void matchingRoomDeleted(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.matchingSucceeded");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceMatchingDto kafkaProduceMatchingDto = objectMapper.readValue(kafkaMessage, KafkaProduceMatchingDto.class);

        notificationService.successMatching(kafkaProduceMatchingDto);
    }

    @Transactional
    @KafkaListener(topics = "matchingCanceled")
    public void matchingCanceled(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.matchingCanceled");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceCancelMatchingDto kafkaProduceCancelMatchingDto = objectMapper.readValue(kafkaMessage, KafkaProduceCancelMatchingDto.class);

        notificationService.cancelMatching(kafkaProduceCancelMatchingDto);
    }

    @Transactional
    @KafkaListener(topics = "gameStarted")
    public void gameStarted(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.gameStarted");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceStartGameDto kafkaProduceStartGameDto = objectMapper.readValue(kafkaMessage, KafkaProduceStartGameDto.class);

        notificationService.startGame(kafkaProduceStartGameDto);
    }

    @Transactional
    @KafkaListener(topics = "gameEnded")
    public void gameEnded(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.gameEnded");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceEndGameDto kafkaProduceEndGameDto = objectMapper.readValue(kafkaMessage, KafkaProduceEndGameDto.class);

        notificationService.endGame(kafkaProduceEndGameDto);
    }

    @Transactional
    @KafkaListener(topics = "gameEndedDraw")
    public void gameEndedDraw(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.gameEndedDraw");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceEndGameDrawDto kafkaProduceEndGameDrawDto = objectMapper.readValue(kafkaMessage, KafkaProduceEndGameDrawDto.class);

        notificationService.endGameDraw(kafkaProduceEndGameDrawDto);
    }





}
