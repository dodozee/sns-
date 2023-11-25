package com.withsports.matchingservice.domain.matchingroom.entity;


import com.withsports.matchingservice.domain.matchingroom.dto.CreateMatchingRoomDto;
import com.withsports.matchingservice.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "matching_room")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
public class MatchingRoom extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "matching_room_id")
    private Long id;

    @Column(name = "matching_room_name")
    private String matchingRoomName;//매칭방 제목 겹치지 않게 설정

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "team_name")
    private String teamName;

    @Column(name = "room_leader_id")
    private Long roomLeaderId;

    @Column(name = "room_leader_nickname")
    private String roomLeaderNickname;

    @OneToMany(mappedBy = "matchingRoom", cascade = CascadeType.ALL)
    private List<RoomUser> roomUsers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MatchingRoomStatus status;

    @Column(name = "sum_score")
    @ColumnDefault(value = "0")
    private Long sumRating;

    private String area;

    private String sports; //

    @Column(name = "max_user_count")
    private Long capacity;// 최대 인원, 방이 가득 차야지 가능
    private Long userCount;// 현재 인원



    public static MatchingRoom createMatchingRoom(Long roomLeaderId, String nickname, String matchingRoomName ,Long teamId, String teamName, Long rating, CreateMatchingRoomDto createMatchingRoomDto) {

        MatchingRoom matchingRoom = new MatchingRoom();
        matchingRoom.roomLeaderId = roomLeaderId;
        matchingRoom.matchingRoomName = matchingRoomName;
        matchingRoom.roomLeaderNickname = nickname;
        matchingRoom.teamId = teamId;
        matchingRoom.teamName = teamName;
        matchingRoom.area = createMatchingRoomDto.getArea();
        matchingRoom.sports = createMatchingRoomDto.getSports();
        matchingRoom.sumRating= rating;
        matchingRoom.capacity = createMatchingRoomDto.getCapacity();
        matchingRoom.userCount = 1L;
        matchingRoom.status = MatchingRoomStatus.WAITING;
        return matchingRoom;
//                .roomLeaderId(roomLeaderId)
//                .title(title)
//                .teamId(teamId)
//                .teamName(teamName)
//                .area(createMatchingRoomDto.getArea())
//                .sports(createMatchingRoomDto.getSports())
//                .capacity(createMatchingRoomDto.getCapacity())
//                .userCount(1L)
//                .status(MatchingRoomStatus.WAITING)
//                .build();


    }


    public void addRoomUser(RoomUser roomUser) {
        roomUsers.add(roomUser);
        roomUser.setMatchingRoom(this);
    }

    public static MatchingRoom of(Long teamId, String teamName, Long roomLeaderId, String roomLeaderNickname, String area, String sports, Long capacity) {
        MatchingRoom matchingRoom = new MatchingRoom();
        matchingRoom.teamId = teamId;
        matchingRoom.teamName = teamName;
        matchingRoom.roomLeaderId = roomLeaderId;
        matchingRoom.roomLeaderNickname = roomLeaderNickname;
        matchingRoom.area = area;
        matchingRoom.sports = sports;
        matchingRoom.capacity = capacity;
        matchingRoom.userCount = 1L;
        matchingRoom.status = MatchingRoomStatus.WAITING;
        return matchingRoom;
    }

    public void addUserCount() {
        this.userCount++;
        if(this.userCount == (long)this.capacity) {
            this.status = MatchingRoomStatus.FULL;
        }
    }

    public void addSumRating(Long rating) {
        this.sumRating += rating;
    }

    public void minusUserCount() {
        this.userCount--;
        if(this.userCount == 0) {
            this.status = MatchingRoomStatus.NONE;
        }
    }

    public void canceled(){
        this.status = MatchingRoomStatus.CANCELED;
    }

    public void full() {
        this.status = MatchingRoomStatus.FULL;
    }

    public void waiting() {
        this.status = MatchingRoomStatus.WAITING;
    }


    public void removeRoomUser(RoomUser roomUser) {
        roomUsers.remove(roomUser);
        roomUser.setMatchingRoom(null);
        minusUserCount();
    }



    public void changeMatchingSearchingStatus() {
        this.status = MatchingRoomStatus.SEARCHING;

    }

    public void changeMatchingCancelStatus() {
        this.status = MatchingRoomStatus.CANCELED;
    }

    public void changeMatchingSuccessStatus() {
        this.status = MatchingRoomStatus.MATCHED;
    }

    public void changeGameStartStatus() { this.status = MatchingRoomStatus.STARTED; }

    public void changeGameEndStatus() { this.status = MatchingRoomStatus.FINISHED; }

    public void changeRoomLeaderNickname(String nickname) {
        this.roomLeaderNickname = nickname;
    }

    public void changeBookingCompletedStatus() {
        this.status = MatchingRoomStatus.BOOKING;
    }
}

