package com.withsports.teamservice.domain.team.entity;

import com.withsports.teamservice.domain.teammember.entity.TeamMember;
import com.withsports.teamservice.global.entity.BaseTimeEntity;
import com.withsports.teamservice.global.entity.Image;
import com.withsports.teamservice.global.entity.Sports;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
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
//@EqualsAndHashCode(of = "id", callSuper = false)
public class Team extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name="team_id")
    private Long id;

    @Column(name="team_name")
    private String teamName;
    //팀장 pk
    @Column(name="team_leader_id")
    private Long leaderId;

    private String introduction;

    private String imageUrl;

    private String area;

    private String sports;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamMember> teamMembers = new ArrayList<>();

    //연관관계 편의 메소드
    public void addTeamMember(TeamMember teamMember) {
        teamMembers.add(teamMember);
        teamMember.setTeam(this);
    }
    public static Team of(String teamName, Long leaderId, String introduction, String imageUrl, String area, String sports){
        Team team = new Team();
        team.teamName = teamName;
        team.leaderId = leaderId;
        team.introduction = introduction;
        team.imageUrl = uploadImage(imageUrl);
        team.area = area;
        team.sports = sports;
        return team;
    }

    public static String uploadImage(String uploadedImage) {
        if (uploadedImage != null && !uploadedImage.isEmpty()) {
            //이미지 주소 저장
            return uploadedImage;
        }
        // TODO : null(x) -> 기본 이미지 주소 저장
        return null;
    }



}
