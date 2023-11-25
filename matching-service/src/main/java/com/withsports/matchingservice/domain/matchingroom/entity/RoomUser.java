package com.withsports.matchingservice.domain.matchingroom.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "room_user")
@NoArgsConstructor
public class RoomUser {

    @Id @GeneratedValue
    @Column(name = "room_user_id")
    private Long id;

    @Column(name = "team_id")
    private Long teamId;



    @Column(name = "user_id")
    private Long userId;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long rating;

    private String tier;

    @Column(name = "participate_status")
    @ColumnDefault(value = "false")
    private boolean participateStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_room_id")
    private MatchingRoom matchingRoom;

    public void setMatchingRoom(MatchingRoom matchingRoom) {
        this.matchingRoom = matchingRoom;
    }
    public static RoomUser of(Long userId, Long teamId, String nickname, Role role, Long rating, String tier) {
        RoomUser roomUser = new RoomUser();
        roomUser.userId = userId;
        roomUser.teamId = teamId;
        roomUser.nickname = nickname;
        roomUser.role = role;
        roomUser.rating = rating;
        roomUser.tier = tier;
        roomUser.participateStatus = true;
        return roomUser;
    }

    public void participateRoom() {
        this.participateStatus = true;
    }


    public boolean getParticipateStatus() {
        return participateStatus;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }
}
