package com.withsports.teamservice.domain.teamuser.web.response;

import com.withsports.teamservice.domain.teamuser.dto.TeamUserApplicationDto;
import com.withsports.teamservice.domain.teamuser.dto.TeamUserDetailDto;
import com.withsports.teamservice.global.client.record.TeamRecordResponse;
import com.withsports.teamservice.global.client.user.GetUserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamUserDetailResponse {
    private Long teamId;//팀 아이디
    private Long userId;//유저 아이디
    private String nickname;//유저 닉네임
    private String introduction;//유저 소개글
    private String role;//유저 역할
    private String profileImage;//유저 프로필 이미지
    private String area;//유저 활동지역
    private Double winRate;//유저 승률
    private String tier;//유저 티어
    private Long mvpCount;//유저 mvp 횟수


    public TeamUserDetailResponse(TeamUserApplicationDto teamUserDto, GetUserResponse getUserResponse, TeamRecordResponse getRecordResponse) {
        this.teamId = teamUserDto.getTeamId();
        this.userId = teamUserDto.getUserId();
        this.nickname = getUserResponse.getNickname();
        this.introduction = getUserResponse.getIntroduction();
        this.role = teamUserDto.getRole().name();
        this.profileImage = getUserResponse.getProfileImage();
        this.area = getUserResponse.getArea();
        this.winRate = getRecordResponse.getWinRate();
    }

    public TeamUserDetailResponse(TeamUserApplicationDto teamUserDto){
        this.teamId = teamUserDto.getTeamId();
        this.userId = teamUserDto.getUserId();
        this.role = teamUserDto.getRole().name();
    }

    public TeamUserDetailResponse(TeamUserDetailDto listDto) {
        this.teamId = listDto.getTeamId();
        this.userId = listDto.getUserId();
        this.nickname = listDto.getNickname();
        this.introduction = listDto.getIntroduction();
        this.role = listDto.getRole();
        this.profileImage = listDto.getProfileImage();
        this.area = listDto.getArea();
        this.winRate = listDto.getWinRate();
        this.tier = listDto.getTier();
        this.mvpCount = listDto.getMvpCount();
    }


    public static List<TeamUserDetailResponse> listOf(List<TeamUserApplicationDto> teamUserDtoList) {
        return null;
    }
}
