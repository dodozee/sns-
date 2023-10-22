package com.withsports.teamservice.domain.teammember.entity;

import com.withsports.teamservice.domain.team.entity.Team;
import com.withsports.teamservice.global.entity.BaseTimeEntity;
import com.withsports.teamservice.global.entity.Sport;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "team_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeamMember extends BaseTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_id")
    private Long id;

    //신청 또는 만든 user의  pk
    private Long userId;

    private Role role;

    //팀 명
    private String name;

    //팀 종목
    @Enumerated(EnumType.STRING)
    private Sport sport;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_id")
    private Team team;


//    @CreatedDate
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    private LocalDateTime updatedAt;

    public void setTeam(Team team) {
        this.team = team;
    }
}
