package com.withsports.teamservice.domain.team.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.teamservice.domain.team.dto.producer.KafkaProduceUpdateUserProfileDto;
import com.withsports.teamservice.domain.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class TeamConsumer {

    private final TeamService teamService;
    private final ObjectMapper objectMapper;



    @Transactional
    @KafkaListener(topics = "updatedUserForTeam")
    public void updatedUserRecord(String kafkaMessage) throws JsonProcessingException {
        log.debug("## UserRecordConsumer.updatedUserRecord");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto = objectMapper.readValue(kafkaMessage, KafkaProduceUpdateUserProfileDto.class);

        teamService.updateUserTeam(kafkaProduceUpdateUserProfileDto);

    }




}
