package com.withsports.recordservice.domain.record.team.entity;

import com.withsports.recordservice.domain.record.matching.entity.MatchingRecord;
import com.withsports.recordservice.domain.record.team.dto.producer.KafkaProduceInitTeamRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceDrawRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceRecordDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "team_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamRecord {

    @Id @GeneratedValue
    @Column(name = "team_record_id")
    private Long id;
    @Column(name = "team_id")
    private Long teamId;
    @Column(name = "team_name")
    private String teamName;
    private String sports;
    private Long win;
    private Long lose;
    private Long draw;
    @OneToMany(mappedBy = "teamRecords", cascade = CascadeType.ALL)
    private List<MatchingRecord> matchingRecords = new ArrayList<>();

    public void addMatchingRecord(MatchingRecord matchingRecord) {
        this.matchingRecords.add(matchingRecord);
        matchingRecord.setTeamRecord(this);
    }

    public static TeamRecord init(KafkaProduceInitTeamRecordDto kafkaProduceInitTeamRecordDto) {
        TeamRecord teamRecord = new TeamRecord();
        teamRecord.teamId = kafkaProduceInitTeamRecordDto.getTeamId();
        teamRecord.teamName = kafkaProduceInitTeamRecordDto.getTeamName();
        teamRecord.sports = kafkaProduceInitTeamRecordDto.getSports();
        teamRecord.win = 0L;
        teamRecord.lose = 0L;
        teamRecord.draw = 0L;
        return teamRecord;
    }


    //TODO 2승씩 쌓인다거나 중복되어서 저장되면 여기가 문제일 확률이 높음
    public void updateByWinTeam(KafkaProduceRecordDto kafkaProduceRecordDto) {
        this.win += 1;
        addMatchingRecord(MatchingRecord.of(kafkaProduceRecordDto));
    }

    public void updateByLoseTeam(KafkaProduceRecordDto kafkaProduceRecordDto) {
        this.lose += 1;
        addMatchingRecord(MatchingRecord.of(kafkaProduceRecordDto));
    }

    public void updateByDrawTeam(KafkaProduceDrawRecordDto kafkaProduceRecordDto) {
        this.draw += 1;
        addMatchingRecord(MatchingRecord.draw(kafkaProduceRecordDto));
    }
}
