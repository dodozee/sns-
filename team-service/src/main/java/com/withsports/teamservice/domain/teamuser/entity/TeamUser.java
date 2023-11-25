package com.withsports.teamservice.domain.teamuser.entity;


import com.withsports.teamservice.domain.team.entity.Team;
import com.withsports.teamservice.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "team_user")
@Getter
@NoArgsConstructor
@EntityListeners(value = {TeamUserListener.class, AuditingEntityListener.class})
public class TeamUser extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "team_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    //    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
    @Column(name = "user_id")
    private Long userId;


    private String nickname;

    @Enumerated(EnumType.STRING)
    private TeamUserStatus status;

    private LocalDateTime appliedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String introduction;

    public void setTeam(Team team) {
        this.team = team;

    }

    public static TeamUser of(Long userId, String nickname, Role role, String introduction) {
        TeamUser teamUser = new TeamUser();
        teamUser.nickname = nickname;
        teamUser.userId = userId;
        teamUser.role = role;
        teamUser.status = TeamUserStatus.NONE;
        teamUser.introduction = introduction;
        teamUser.appliedAt = LocalDateTime.now();
        return teamUser;
    }

    public static TeamUser of(Long userId, String nickname, Role role, TeamUserStatus status) {
        TeamUser teamUser = new TeamUser();
        teamUser.nickname = nickname;
        teamUser.userId = userId;
        teamUser.role = role;
        teamUser.status = status;
        teamUser.appliedAt = LocalDateTime.now();
        return teamUser;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // 팀 가입 신청을 했을 때 status를 PLACED로 바꿔줌
    public void placed() {
        this.status = TeamUserStatus.PLACED;
    }


    // 팀 가입 신청 받아주면 status를 ACCEPTED로 바꿔줌
    public void accept() {
        this.status = TeamUserStatus.ACCEPTED;
    }

    // 팀 가입 신청 거절하면 status를 REJECTED로 바꿔줌
    public void reject() {
        this.status = TeamUserStatus.REJECTED;
    }
}

