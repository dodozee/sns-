package com.withsports.matchingservice.domain.matchingroom.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.matchingservice.domain.matchingroom.dto.produce.KafkaProduceRoomFullDto;
import com.withsports.matchingservice.domain.matchingroom.dto.produce.KafkaProduceRoomUserListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class MatchingRoomProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public void deletedMatchingRoom(List<Long> teamUser, String roomTitle) throws Exception{
        KafkaProduceRoomUserListDto kafkaSendTeamDto = KafkaProduceRoomUserListDto.builder()
                .userIdList(teamUser)
                .roomTitle(roomTitle)
                .build();
        String json = objectMapper.writeValueAsString(kafkaSendTeamDto);
        kafkaTemplate.send("matchingRoomDeleted", json);
        log.info("[MatchingRoomProducer] matchingRoomDeleted = {}", json);
    }

    public void fullMatchingRoom(Long roomLeaderId, String title, Long capacity) throws Exception{
        KafkaProduceRoomFullDto kafkaProduceRoomFullDto = KafkaProduceRoomFullDto.builder()
                .roomLeaderId(roomLeaderId)
                .title(title)
                .capacity(capacity)
                .build();
        String json = objectMapper.writeValueAsString(kafkaProduceRoomFullDto);
        kafkaTemplate.send("matchingRoomFulled", json);
        log.info("[MatchingRoomProducer] matchingRoomFulled = {}", json);
    }
}

