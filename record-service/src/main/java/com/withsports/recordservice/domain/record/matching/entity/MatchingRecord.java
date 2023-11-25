package com.withsports.recordservice.domain.record.matching.entity;

import com.withsports.recordservice.domain.record.team.entity.TeamRecord;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceDrawRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceRecordDto;
import com.withsports.recordservice.domain.record.user.entity.UserRecord;
import com.withsports.recordservice.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matching_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class MatchingRecord extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "matching_record_id")
    private Long id;
    @Column(name = "matching_room_id")
    private Long matchingRoomId;
    private String sports;
    @Column(name = "win_team_id")
    private Long winTeamId;
    @Column(name = "win_team_name")
    private String winTeamName;
    @Column(name = "lose_team_id")
    private Long loseTeamId;
    @Column(name = "lose_team_name")
    private String loseTeamName;
    private String result;
    @Column(name = "win_score")
    private Long winScore;
    @Column(name = "lose_score")
    private Long loseScore;
    private Long drawTeamId1;
    private String drawTeamName1;
    private Long drawTeamId2;
    private String drawTeamName2;
    private String area;
    @OneToMany(mappedBy = "matchingRecord", cascade = CascadeType.ALL)
    private List<UserRecord> userRecords = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_record_id")
    private TeamRecord teamRecords;

    public void addUserRecord(UserRecord userRecord) {
        this.userRecords.add(userRecord);
        userRecord.setMatchingRecord(this);
    }

    public void setTeamRecord(TeamRecord teamRecord){
        this.teamRecords = teamRecord;
    }

    public static MatchingRecord of(KafkaProduceRecordDto kafkaProduceRecordDto) {
        MatchingRecord matchingRecord = new MatchingRecord();
        matchingRecord.matchingRoomId = kafkaProduceRecordDto.getMatchingRoomId();
        matchingRecord.sports = kafkaProduceRecordDto.getSports();
        matchingRecord.winTeamId = kafkaProduceRecordDto.getWinTeamId();
        matchingRecord.winTeamName = kafkaProduceRecordDto.getWinTeamName();
        matchingRecord.loseTeamId = kafkaProduceRecordDto.getLoseTeamId();
        matchingRecord.loseTeamName = kafkaProduceRecordDto.getLoseTeamName();
        matchingRecord.result = kafkaProduceRecordDto.getResult();
        matchingRecord.winScore =  kafkaProduceRecordDto.getWinScore();
        matchingRecord.loseScore = kafkaProduceRecordDto.getLoseScore();
        matchingRecord.area = kafkaProduceRecordDto.getArea();
        return matchingRecord;
    }

    public static MatchingRecord of(KafkaProduceDrawRecordDto kafkaProduceDrawRecordDto) {
        MatchingRecord matchingRecord = new MatchingRecord();
        matchingRecord.matchingRoomId = kafkaProduceDrawRecordDto.getMatchingRoomId();
        matchingRecord.sports = kafkaProduceDrawRecordDto.getSports();
        matchingRecord.winTeamId = kafkaProduceDrawRecordDto.getOneDrawTeamId();
        matchingRecord.winTeamName = kafkaProduceDrawRecordDto.getOneDrawTeamName();
        matchingRecord.loseTeamId = kafkaProduceDrawRecordDto.getTwoDrawTeamId();
        matchingRecord.loseTeamName = kafkaProduceDrawRecordDto.getTwoDrawTeamName();
        matchingRecord.result = kafkaProduceDrawRecordDto.getResult();
        matchingRecord.winScore =  kafkaProduceDrawRecordDto.getOneScore();
        matchingRecord.loseScore = kafkaProduceDrawRecordDto.getTwoScore();
        matchingRecord.area = kafkaProduceDrawRecordDto.getArea();
        return matchingRecord;
    }

    public static MatchingRecord draw(KafkaProduceDrawRecordDto kafkaProduceDrawRecordDto) {
        MatchingRecord matchingRecord = new MatchingRecord();
        matchingRecord.matchingRoomId = kafkaProduceDrawRecordDto.getMatchingRoomId();
        matchingRecord.sports = kafkaProduceDrawRecordDto.getSports();
        matchingRecord.drawTeamId1 = kafkaProduceDrawRecordDto.getOneDrawTeamId();
        matchingRecord.drawTeamName1 = kafkaProduceDrawRecordDto.getOneDrawTeamName();
        matchingRecord.drawTeamId2 = kafkaProduceDrawRecordDto.getTwoDrawTeamId();
        matchingRecord.drawTeamName2 = kafkaProduceDrawRecordDto.getTwoDrawTeamName();
        matchingRecord.result = kafkaProduceDrawRecordDto.getResult();
        matchingRecord.winScore =  kafkaProduceDrawRecordDto.getOneScore();
        matchingRecord.loseScore = kafkaProduceDrawRecordDto.getTwoScore();
        matchingRecord.area = kafkaProduceDrawRecordDto.getArea();
        return matchingRecord;
    }


}
