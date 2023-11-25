package com.withsports.matchingservice.domain.matching.dto;


import com.withsports.matchingservice.domain.matching.dto.request.EndGameResultRequest;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndGameResultDto {
    private Long matchingRoomId;//매칭방 아이디
    private Long roomLeaderId; //방장 아이디
    private Long score; //팀 점수
    private Long opponentScore; //상대팀 점수
    private String result; //승/무/패
    //    private Long teamId; //팀 아이디
//    private Long opponentTeamId; //상대팀 아이디


    public static EndGameResultDto of(EndGameResultRequest endGameResultRequest, Long matchingRoomId, Long roomLeaderId) {
        return EndGameResultDto.builder()
                .matchingRoomId(matchingRoomId)
                .roomLeaderId(roomLeaderId)
                .score(endGameResultRequest.getScore())
                .opponentScore(endGameResultRequest.getOpponentScore())
                .result(endGameResultRequest.getResult())
                .build();
    }
    //                .teamId(endGameResultRequest.getTeamId())
//                .opponentTeamId(endGameResultRequest.getOpponentTeamId())
}
