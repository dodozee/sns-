package com.withsports.teamservice.domain.team.entity;

import com.withsports.teamservice.domain.teamuser.entity.TeamUser;
import com.withsports.teamservice.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)

//@EqualsAndHashCode(of = "id", callSuper = false)
public class Team extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name="team_id")
    private Long id;

    @Column(name="team_name")
    private String teamName;
    //팀장 pk
    @Column(name="leader_id")
    private Long leaderId;

    @Column(name="leader_name")
    private String leaderName;

    private String introduction;

    private String imageUrl;

    private String area;

    private String sports;

    private Long teamMemberCount;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamUser> teamUsers = new ArrayList<>();

    //연관관계 편의 메소드
    public void addTeamUser(TeamUser teamUser) {
        teamUsers.add(teamUser);
        teamUser.setTeam(this);
    }
    public static Team of(String teamName, Long leaderId, String leaderName, String introduction, String area, String sports){
        Team team = new Team();
        team.teamName = teamName;
        team.leaderId = leaderId;
        team.leaderName = leaderName;
        team.introduction = introduction;
        team.area = area;
        team.sports = sports;
        team.teamMemberCount = 0L;
        return team;
    }

    public void setImage(String uploadedImage) {
        this.imageUrl = uploadedImage;
    }


    public void updateTeamProfile(String teamName, String introduction, String area, String sports) {
        this.teamName = teamName;
        this.introduction = introduction;
        this.area = area;
        this.sports = sports;
    }

    public void changeLeaderName(String nickname) {
        this.leaderName = nickname;
    }

    public void addTeamCount() {
        this.teamMemberCount++;
    }
}
