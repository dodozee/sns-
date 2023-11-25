package com.withsports.teamservice.domain.teamuser.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamUserDetailDto {

    private Long teamId;//팀 아이디
    private Long userId;//유저 아이디
    private String nickname;//유저 닉네임
    private String introduction;//유저 소개글
    private String role;//유저 역할
    private String profileImage;//프로필 이미지
    private String area;//활동지역
    private Double winRate;//승률
    private String tier;//티어
    private Long mvpCount; //mvp 횟수


    @Builder
    public TeamUserDetailDto(Long teamId, Long userId, String nickname, String introduction, String role, String profileImage, String area, Double winRate, String tier, Long mvpCount) {
        this.teamId = teamId;
        this.userId = userId;
        this.nickname = nickname;
        this.introduction = introduction;
        this.role = role;
        this.profileImage = profileImage;
        this.area = area;
        this.winRate = winRate;
        this.tier = tier;
        this.mvpCount = mvpCount;


    }

    public static TeamUserDetailDto of(Long teamId, Long userId, String nickname, String introduction, String role, String profileImage, String area, Double winRate, String tier, Long mvpCount) {
        return TeamUserDetailDto.builder()
                .teamId(teamId)
                .userId(userId)
                .nickname(nickname)
                .introduction(introduction)
                .role(role)
                .profileImage(profileImage)
                .area(area)
                .winRate(winRate)
                .build();
    }

}