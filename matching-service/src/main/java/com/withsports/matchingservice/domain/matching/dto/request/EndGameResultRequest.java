package com.withsports.matchingservice.domain.matching.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EndGameResultRequest {
//    private Long teamId; //팀 아이디
//    private Long opponentTeamId; //상대팀 아이디
    private Long score; //팀 점수
    private Long opponentScore; //상대팀 점수
    private String result; //승/무/패

}
