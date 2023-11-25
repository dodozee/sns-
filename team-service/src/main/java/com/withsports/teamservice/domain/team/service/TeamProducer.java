package com.withsports.teamservice.domain.team.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.teamservice.domain.team.dto.producer.KafkaProduceInitTeamRecordDto;
import com.withsports.teamservice.domain.team.dto.producer.KafkaProduceTeamDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void teamCreated(Long teamId, String teamName, String sports) throws Exception {
        KafkaProduceInitTeamRecordDto kafkaProduceInitTeamRecordDto = KafkaProduceInitTeamRecordDto.builder()
                .teamId(teamId)
                .teamName(teamName)
                .sports(sports)
                .build();
        String json = objectMapper.writeValueAsString(kafkaProduceInitTeamRecordDto);
        kafkaTemplate.send("teamCreated", json);
        log.info("[TeamSender] teamCreated = {}", json);
    }

    public void teamDeleted(String teamName, Long teamId, List<Long> userIds) throws Exception {
        KafkaProduceTeamDto kafkaProduceTeamDto = KafkaProduceTeamDto.builder()
                .teamName(teamName)
                .teamId(teamId)
                .userIds(userIds)
                .build();
        String json = objectMapper.writeValueAsString(kafkaProduceTeamDto);
        kafkaTemplate.send("teamDeleted", json);
        log.info("[TeamSender] teamDeleted = {}", json);
    }
}
