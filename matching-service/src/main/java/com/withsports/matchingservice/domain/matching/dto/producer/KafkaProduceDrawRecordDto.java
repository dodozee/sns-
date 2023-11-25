package com.withsports.matchingservice.domain.matching.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class KafkaProduceDrawRecordDto {
    private List<Long> userIds;
    private String sports;
    private Long matchingRoomId;
    private Long oneDrawTeamId;
    private String oneDrawTeamName;
    private Long twoDrawTeamId;
    private String twoDrawTeamName;
    private String result;
    private Long oneScore;
    private Long twoScore;
    private String area;

    public static KafkaProduceDrawRecordDto of(KafkaProduceRecordDto kafkaProduceRecordDto) {
        KafkaProduceDrawRecordDto kafkaProduceDrawRecordDto = new KafkaProduceDrawRecordDto();
        kafkaProduceDrawRecordDto.userIds = kafkaProduceRecordDto.getUserIds();
        kafkaProduceDrawRecordDto.sports = kafkaProduceRecordDto.getSports();
        kafkaProduceDrawRecordDto.matchingRoomId = kafkaProduceRecordDto.getMatchingRoomId();
        kafkaProduceDrawRecordDto.oneDrawTeamId = kafkaProduceRecordDto.getWinTeamId();
        kafkaProduceDrawRecordDto.oneDrawTeamName = kafkaProduceRecordDto.getWinTeamName();
        kafkaProduceDrawRecordDto.twoDrawTeamId = kafkaProduceRecordDto.getLoseTeamId();
        kafkaProduceDrawRecordDto.twoDrawTeamName = kafkaProduceRecordDto.getLoseTeamName();
        kafkaProduceDrawRecordDto.result = kafkaProduceRecordDto.getResult();
        kafkaProduceDrawRecordDto.oneScore = kafkaProduceRecordDto.getWinScore();
        kafkaProduceDrawRecordDto.twoScore = kafkaProduceRecordDto.getLoseScore();
        kafkaProduceDrawRecordDto.area = kafkaProduceRecordDto.getArea();
        return kafkaProduceDrawRecordDto;
    }
}
