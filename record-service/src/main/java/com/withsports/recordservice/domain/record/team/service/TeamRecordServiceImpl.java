package com.withsports.recordservice.domain.record.team.service;


import com.withsports.recordservice.domain.record.matching.dto.TeamRecordDto;
import com.withsports.recordservice.domain.record.team.dto.producer.KafkaProduceInitTeamRecordDto;
import com.withsports.recordservice.domain.record.team.entity.TeamRecord;
import com.withsports.recordservice.domain.record.team.repository.TeamRecordRepository;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceDrawRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceRecordDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamRecordServiceImpl implements TeamRecordService {

    private final TeamRecordRepository teamRecordRepository;
    @Override
    public TeamRecordDto getRecordByTeamId(Long teamId) {
        TeamRecord teamRecord = teamRecordRepository.findRecordByTeamId(teamId);
       return TeamRecordDto.of(teamRecord);

    }

    @Transactional
    @Override
    public void initTeamRecord(KafkaProduceInitTeamRecordDto kafkaProduceInitTeamRecordDto) {
        TeamRecord teamRecord = TeamRecord.init(kafkaProduceInitTeamRecordDto);
        teamRecordRepository.save(teamRecord);
    }

    @Transactional
    @Override
    public void updateTeamRecordByWinTeam(KafkaProduceRecordDto kafkaProduceRecordDto) {
        TeamRecord teamRecord = teamRecordRepository.findRecordByTeamId(kafkaProduceRecordDto.getWinTeamId());
        teamRecord.updateByWinTeam(kafkaProduceRecordDto);
    }

    @Transactional
    @Override
    public void updateTeamRecordByLoseTeam(KafkaProduceRecordDto kafkaProduceRecordDto) {
        TeamRecord teamRecord = teamRecordRepository.findRecordByTeamId(kafkaProduceRecordDto.getLoseTeamId());
        teamRecord.updateByLoseTeam(kafkaProduceRecordDto);

    }

    @Transactional
    @Override
    public void updateTeamRecordByDrawTeam(KafkaProduceDrawRecordDto kafkaProduceDrawRecordDto) {
        TeamRecord teamRecord = teamRecordRepository.findRecordByTeamId(kafkaProduceDrawRecordDto.getOneDrawTeamId());
        teamRecord.updateByDrawTeam(kafkaProduceDrawRecordDto);
    }
}
