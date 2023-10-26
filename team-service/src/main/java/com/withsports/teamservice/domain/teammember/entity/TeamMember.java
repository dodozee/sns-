package com.withsports.teamservice.domain.teammember.entity;

import com.withsports.teamservice.domain.team.entity.Team;
import com.withsports.teamservice.global.entity.BaseTimeEntity;
import com.withsports.teamservice.global.entity.Role;
import com.withsports.teamservice.global.entity.Sports;
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

    @Column(name = "user_id")
    //신청 또는 만든 user의  pk
    private Long userId;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String sports;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;



    public void setTeam(Team team) {
        this.team = team;
//        team.getTeamMembers().add(this);
    }


    public static TeamMember of(Long userId, Role role, String sports, Team team){
        TeamMember teamMember = new TeamMember();
        teamMember.userId = userId;
        teamMember.role = role;
        teamMember.sports = sports;
        teamMember.team=team;

        return teamMember;
    }
}
