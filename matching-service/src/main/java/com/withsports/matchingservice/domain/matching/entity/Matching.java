package com.withsports.matchingservice.domain.matching.entity;


import com.withsports.matchingservice.domain.matchingroom.entity.MatchingRoomStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name="matching")
@EntityListeners(value = {MatchingListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching {

    @Id @GeneratedValue
    @Column(name="matching_id")
    private Long id;

    @Column(name ="matching_room_id1")
    private Long matchingRoomId1;
    @Column(name ="matching_room_id2")
    private Long matchingRoomId2;

    @Column(name ="team_id1")
    private Long teamId1;
    @Column(name ="team_id2")
    private Long teamId2;
    private Long totalCapacity;
    private String sports;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    public Matching(List<com.withsports.matchingservice.domain.matching.redis.Matching> matchings) {
        this.matchingRoomId1=Long.valueOf(matchings.get(0).getMatchingRoomId());
        this.matchingRoomId2=Long.valueOf(matchings.get(1).getMatchingRoomId());
        this.teamId1 = matchings.get(0).getTeamId();
        this.teamId2 = matchings.get(1).getTeamId();
        this.totalCapacity = matchings.get(0).getCapacity()*2;
        this.sports = matchings.get(0).getSports();
        this.matchingStatus = MatchingStatus.BEFORE_BOOKING;
    }

    public void changeMatchingStatusToBeforeBooking(){
        this.matchingStatus =  MatchingStatus.BEFORE_BOOKING;
    }
    public void changeMatchingStatusToAfterBooking(){
        this.matchingStatus =  MatchingStatus.AFTER_BOOKING;
    }

    public void changeMatchingStatusToStartPlay(){
        this.matchingStatus =  MatchingStatus.STARTED;
    }

    public void changeMatchingStatusToFinishPlay(){
        this.matchingStatus =  MatchingStatus.FINISHED;
    }
}
