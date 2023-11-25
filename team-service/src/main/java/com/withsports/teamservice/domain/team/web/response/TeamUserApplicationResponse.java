package com.withsports.teamservice.domain.team.web.response;


import com.withsports.teamservice.domain.teamuser.dto.TeamUserApplicationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamUserApplicationResponse {

        private Long teamId; //신청한 팀 아이디
        private Long userId; //신청한 유저 아이디
        private String introduction; //자기소개
        private Long elapsedTime;  //경과된 시간

        public TeamUserApplicationResponse(TeamUserApplicationDto teamUserApplicationDto) {
                this.teamId = teamUserApplicationDto.getTeamId();
                this.userId = teamUserApplicationDto.getUserId();
                this.introduction = teamUserApplicationDto.getIntroduction();
                this.elapsedTime = teamUserApplicationDto.getElapsedTime();
        }
}
