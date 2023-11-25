package com.withsports.recordservice.domain.record.team.messagequeue;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.recordservice.domain.record.team.dto.producer.KafkaProduceInitTeamRecordDto;
import com.withsports.recordservice.domain.record.team.service.TeamRecordService;
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
public class TeamRecordConsumer {

    private final TeamRecordService teamRecordService;
    private final ObjectMapper objectMapper;

    @Transactional
    @KafkaListener(topics = "teamCreated")
    public void teamDeleted(String kafkaMessage) throws JsonProcessingException {
        log.debug("## UserRecordConsumer.userCreated");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceInitTeamRecordDto kafkaProduceInitTeamRecordDto = objectMapper.readValue(kafkaMessage, KafkaProduceInitTeamRecordDto.class);

        teamRecordService.initTeamRecord(kafkaProduceInitTeamRecordDto);
    }


}
