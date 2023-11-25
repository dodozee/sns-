package com.withsports.recordservice.domain.record.team.service;

import com.withsports.recordservice.domain.record.matching.dto.TeamRecordDto;
import com.withsports.recordservice.domain.record.team.dto.producer.KafkaProduceInitTeamRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceDrawRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceRecordDto;

public interface TeamRecordService {
    TeamRecordDto getRecordByTeamId(Long teamId);

    void initTeamRecord(KafkaProduceInitTeamRecordDto kafkaProduceInitTeamRecordDto);

    void updateTeamRecordByWinTeam(KafkaProduceRecordDto kafkaProduceRecordDto);

    void updateTeamRecordByLoseTeam(KafkaProduceRecordDto kafkaProduceRecordDto);

    void updateTeamRecordByDrawTeam(KafkaProduceDrawRecordDto kafkaProduceDrawRecordDto);

}
