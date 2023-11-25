package com.withsports.teamservice.domain.team.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDetailResponse {
    private Long id; //팀 아이디
    private String teamName; //팀 이름
    private String leaderNickname; //팀장 이름
    private String introduction; //소개글
    private String area; //활동지역
    private String sports; //활동종목
    private String imageUrl; //팀 이미지
    private Pageable pageable; //팀원 리스트
    private Long teamMemberCount; //팀원 수

}
