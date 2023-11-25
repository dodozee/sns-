package com.withsports.recordservice.domain.record.user.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceDrawRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceUpdateUserProfileDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceUserDto;
import com.withsports.recordservice.domain.record.user.service.UserRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class UserRecordConsumer {

    private final UserRecordService userRecordService;
    private final ObjectMapper objectMapper;

    @Transactional
    @KafkaListener(topics = "initUserForRecord")
    public void userCreated(String kafkaMessage) throws JsonProcessingException {
        log.debug("## UserRecordConsumer.userCreated");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceUserDto kafkaProduceTeamDto = objectMapper.readValue(kafkaMessage, KafkaProduceUserDto.class);

        userRecordService.initUserRecord(kafkaProduceTeamDto);
    }

    @Transactional
    @KafkaListener(topics = "updatedUserForRecord")
    public void updatedUserRecord(String kafkaMessage) throws JsonProcessingException {
        log.debug("## UserRecordConsumer.updatedUserRecord");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto = objectMapper.readValue(kafkaMessage, KafkaProduceUpdateUserProfileDto.class);

        userRecordService.updateUserRecord(kafkaProduceUpdateUserProfileDto);
    }

    @Transactional
    @KafkaListener(topics = "gameWon" )
    public void win(String kafkaMessage) throws JsonProcessingException {
        log.debug("## UserRecordConsumer.gameWon");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceRecordDto kafkaProduceRecordDto = objectMapper.readValue(kafkaMessage, KafkaProduceRecordDto.class);

        userRecordService.winGame(kafkaProduceRecordDto);
    }

    @Transactional
    @KafkaListener(topics = "gameLost")
    public void lose(String kafkaMessage) throws JsonProcessingException {
        log.debug("## UserRecordConsumer.gameLost");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceRecordDto kafkaProduceRecordDto = objectMapper.readValue(kafkaMessage, KafkaProduceRecordDto.class);

        userRecordService.loseGame(kafkaProduceRecordDto);
    }

    @Transactional
    @KafkaListener(topics = "gameDraw")
    public void draw(String kafkaMessage) throws JsonProcessingException {
        log.debug("## UserRecordConsumer.gameDraw");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceDrawRecordDto kafkaProduceDrawRecordDto = objectMapper.readValue(kafkaMessage, KafkaProduceDrawRecordDto.class);

        userRecordService.drawGame(kafkaProduceDrawRecordDto);
    }



}
