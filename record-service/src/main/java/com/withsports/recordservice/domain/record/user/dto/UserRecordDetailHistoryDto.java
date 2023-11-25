package com.withsports.recordservice.domain.record.user.dto;

import com.withsports.recordservice.domain.record.matching.entity.MatchingRecord;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRecordDetailHistoryDto {
    private Long id;
    private Long matchingRoomId;
    private String sports;
    private Long winTeamId;
    private String winTeamName;
    private Long loseTeamId;
    private String loseTeamName;
    private Long winScore;
    private Long loseScore;
    private Long drawTeamId1;
    private String drawTeamName1;
    private Long drawTeamId2;
    private String drawTeamName2;
    private String area;
    private String result;

    public static UserRecordDetailHistoryDto of(MatchingRecord matchingRecord) {

        if(Objects.equals(matchingRecord.getResult(), "승리")){
           return new UserRecordDetailHistoryDto(
                    matchingRecord.getId(),
                    matchingRecord.getMatchingRoomId(),
                    matchingRecord.getSports(),
                    matchingRecord.getWinTeamId(),
                    matchingRecord.getWinTeamName(),
                    matchingRecord.getLoseTeamId(),
                    matchingRecord.getLoseTeamName(),
                    matchingRecord.getWinScore(),
                    matchingRecord.getLoseScore(),
                    0L,
                    "무승부가 아님",
                   0L,
                   "무승부가 아님",
                    matchingRecord.getArea(),
                    matchingRecord.getResult());

        }else if(Objects.equals(matchingRecord.getResult(), "패배")){
            return new UserRecordDetailHistoryDto(
                    matchingRecord.getId(),
                    matchingRecord.getMatchingRoomId(),
                    matchingRecord.getSports(),
                    matchingRecord.getWinTeamId(),
                    matchingRecord.getWinTeamName(),
                    matchingRecord.getLoseTeamId(),
                    matchingRecord.getLoseTeamName(),
                    matchingRecord.getWinScore(),
                    matchingRecord.getLoseScore(),
                    0L,
                    "무승부가 아님",
                    0L,
                    "무승부가 아님",
                    matchingRecord.getArea(),
                    matchingRecord.getResult());
        }else{
            return new UserRecordDetailHistoryDto(
                    matchingRecord.getId(),
                    matchingRecord.getMatchingRoomId(),
                    matchingRecord.getSports(),
                    0L,
                    "승리가 아님",
                    0L,
                    "패배가 아님",
                    0L,
                    0L,
                    matchingRecord.getDrawTeamId1(),
                    matchingRecord.getDrawTeamName1(),
                    matchingRecord.getDrawTeamId2(),
                    matchingRecord.getDrawTeamName2(),
                    matchingRecord.getArea(),
                    matchingRecord.getResult());
        }

    }
}
