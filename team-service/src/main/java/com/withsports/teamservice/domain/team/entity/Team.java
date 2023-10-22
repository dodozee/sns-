package com.withsports.teamservice.domain.team.entity;

import com.withsports.teamservice.domain.teammember.entity.TeamMember;
import com.withsports.teamservice.global.entity.BaseTimeEntity;
import com.withsports.teamservice.global.entity.Photo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team extends BaseTimeEntity

    @Id @GeneratedValue
    @Column(name="team_id")
    private Long id;

    private String name;
    //팀장 pk
    private Long leaderId;

    private Photo photo;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamMember> teamMembers = new ArrayList<>();

    //== 연관관계 편의 메소드 ==//
    public void addTeamMember(TeamMember teamMember) {
        teamMembers.add(teamMember);
        teamMember.setTeam(this);
    }
//    @CreatedDate
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    private LocalDateTime updatedAt;



}
