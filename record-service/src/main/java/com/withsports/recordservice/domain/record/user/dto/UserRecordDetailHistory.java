package com.withsports.recordservice.domain.record.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRecordDetailHistory {
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

    public static UserRecordDetailHistory of(UserRecordDetailHistoryDto userRecordDetailHistoryDto) {

            if(Objects.equals(userRecordDetailHistoryDto.getResult(), "승리")){
            return new UserRecordDetailHistory(
                        userRecordDetailHistoryDto.getId(),
                        userRecordDetailHistoryDto.getMatchingRoomId(),
                        userRecordDetailHistoryDto.getSports(),
                        userRecordDetailHistoryDto.getWinTeamId(),
                        userRecordDetailHistoryDto.getWinTeamName(),
                        userRecordDetailHistoryDto.getLoseTeamId(),
                        userRecordDetailHistoryDto.getLoseTeamName(),
                        userRecordDetailHistoryDto.getWinScore(),
                        userRecordDetailHistoryDto.getLoseScore(),
                        0L,
                        "무승부가 아님",
                    0L,
                    "무승부가 아님",
                        userRecordDetailHistoryDto.getArea(),
                        userRecordDetailHistoryDto.getResult());

            }else if(Objects.equals(userRecordDetailHistoryDto.getResult(), "패배")) {
                return new UserRecordDetailHistory(
                        userRecordDetailHistoryDto.getId(),
                        userRecordDetailHistoryDto.getMatchingRoomId(),
                        userRecordDetailHistoryDto.getSports(),
                        userRecordDetailHistoryDto.getWinTeamId(),
                        userRecordDetailHistoryDto.getWinTeamName(),
                        userRecordDetailHistoryDto.getLoseTeamId(),
                        userRecordDetailHistoryDto.getLoseTeamName(),
                        userRecordDetailHistoryDto.getWinScore(),
                        userRecordDetailHistoryDto.getLoseScore(),
                        0L,
                        "무승부가 아님",
                        0L,
                        "무승부가 아님",
                        userRecordDetailHistoryDto.getArea(),
                        userRecordDetailHistoryDto.getResult());
            }else{
                return new UserRecordDetailHistory(
                        userRecordDetailHistoryDto.getId(),
                        userRecordDetailHistoryDto.getMatchingRoomId(),
                        userRecordDetailHistoryDto.getSports(),
                        0L,
                        "무승부가 아님",
                        0L,
                        "무승부가 아님",
                        0L,
                        0L,
                        userRecordDetailHistoryDto.getDrawTeamId1(),
                        userRecordDetailHistoryDto.getDrawTeamName1(),
                        userRecordDetailHistoryDto.getDrawTeamId2(),
                        userRecordDetailHistoryDto.getDrawTeamName2(),
                        userRecordDetailHistoryDto.getArea(),
                        userRecordDetailHistoryDto.getResult());
            }
    }
}
