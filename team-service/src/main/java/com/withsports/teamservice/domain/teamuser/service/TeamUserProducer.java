package com.withsports.teamservice.domain.teamuser.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.teamservice.domain.teamuser.dto.Producer.KafkaProduceTeamUserDto;
import com.withsports.teamservice.domain.teamuser.entity.TeamUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class TeamUserProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void applyJoinTeamUser(TeamUser teamUser) throws Exception {
        KafkaProduceTeamUserDto kafkaSendTeamDto = KafkaProduceTeamUserDto.of(teamUser);
        String json = objectMapper.writeValueAsString(kafkaSendTeamDto);
        kafkaTemplate.send("teamUserPlaced", json);
        log.info("[TeamUserProducer] teamUserPlaced = {}", json);
    }

    public void acceptJoinTeamUser(TeamUser teamUser)throws Exception {
        KafkaProduceTeamUserDto kafkaSendTeamDto = KafkaProduceTeamUserDto.of(teamUser);
        String json = objectMapper.writeValueAsString(kafkaSendTeamDto);
        kafkaTemplate.send("teamUserAccepted", json);
        log.info("[TeamUserProducer] teamUserAccepted = {}", json);
    }

    public void rejectedJoinTeamUser(TeamUser teamUser) throws Exception{
        KafkaProduceTeamUserDto kafkaSendTeamDto = KafkaProduceTeamUserDto.of(teamUser);
        String json = objectMapper.writeValueAsString(kafkaSendTeamDto);
        kafkaTemplate.send("teamUserRejected", json);
        log.info("[TeamUserProducer] teamUserRejected = {}", json);
    }
}

