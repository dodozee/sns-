package com.withsports.pointservice.domain.point.messagequeue.matching;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.pointservice.domain.point.dto.producer.*;
import com.withsports.pointservice.domain.point.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class UserPointConsumer {

    private final ObjectMapper objectMapper;
    private final PointService pointService;


    @Transactional
    @KafkaListener(topics = "initUserForPoint")
    public void initUserPoint(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.initUserPoint");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceInitUserDto kafkaProduceInitUserDto = objectMapper.readValue(kafkaMessage, KafkaProduceInitUserDto.class);

        pointService.initUserPoint(kafkaProduceInitUserDto);
    }

    @Transactional
    @KafkaListener(topics = "updatedUserForPoint")
    public void updateUserPoint(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.updateUserPoint");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto = objectMapper.readValue(kafkaMessage, KafkaProduceUpdateUserProfileDto.class);

        pointService.updateUserPoint(kafkaProduceUpdateUserProfileDto);
    }

    @Transactional
    @KafkaListener(topics = "gameWinPointIncreased")
    public void increasePointByGameWin(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.gameWonPointIncreased");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceGameWinPointDto kafkaProduceGameWinPointDto = objectMapper.readValue(kafkaMessage, KafkaProduceGameWinPointDto.class);

        pointService.increasePointByGameWin(kafkaProduceGameWinPointDto);
    }


    @Transactional
    @KafkaListener(topics = "gameLosePointIncreased")
    public void increasePointByGameLose(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.gameWonPointDecreased");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceGameLosePointDto kafkaProduceGameLosePointDto = objectMapper.readValue(kafkaMessage, KafkaProduceGameLosePointDto.class);

        pointService.increasePointByGameLose(kafkaProduceGameLosePointDto);
    }

    @Transactional
    @KafkaListener(topics = "gameDrawPointIncreased")
    public void increasePointByGameDraw(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingNotificationConsumer.gameWonPointDecreased");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceGameDrawPointDto kafkaProduceGameDrawPointDto = objectMapper.readValue(kafkaMessage, KafkaProduceGameDrawPointDto.class);

        pointService.increasePointByGameDraw(kafkaProduceGameDrawPointDto);
    }

//    @Transactional
//    @KafkaListener(topics = "buyGifticonPointDecreased")
//    public void decreasePointByBuyGifticon(String kafkaMessage) throws JsonProcessingException {
//        log.debug("## MatchingNotificationConsumer.buyGifticonPointDecreased");
//        log.debug("#### kafka Message = {}", kafkaMessage);
//
//        KafkaProduceOrderPlacedDto kafkaProduceOrderPlacedDto = objectMapper.readValue(kafkaMessage, KafkaProduceOrderPlacedDto.class);
//
//        pointService.decreasePointByBuyGifticon(kafkaProduceOrderPlacedDto);
//    }


}