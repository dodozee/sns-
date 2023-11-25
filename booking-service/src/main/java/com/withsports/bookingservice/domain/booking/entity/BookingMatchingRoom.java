package com.withsports.bookingservice.domain.booking.entity;


import com.withsports.bookingservice.domain.booking.dto.producer.Matching;
import com.withsports.bookingservice.global.client.matching.GetMatchingRoomResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name="booking_matching_room")
@EntityListeners(value = {BookingMatchingRoomListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookingMatchingRoom {

    @Id @GeneratedValue
    @Column(name="booking_matching_room_id")
    private Long id;
    @Column(name="matching_id")
    private Long matchingId;
    @Column(name = "matching_room_id_1")
    private Long matchingRoomId1;
    @Column(name = "matching_room_name_1")
    private String matchingRoomName1;
    @Column(name = "matching_room_team_id_1")
    private Long matchingRoomTeamId1;
    @Column(name = "matching_room_leader_id_1")
    private Long matchingRoomLeaderId1;
    @Column(name = "matching_room_leader_nickname_1")
    private String matchingRoomLeaderNickname1;
    @Column(name = "matching_room_id_2")
    private Long matchingRoomId2;
    @Column(name = "matching_room_name_2")
    private String matchingRoomName2;
    @Column(name = "matching_room_team_id_2")
    private Long matchingRoomTeamId2;
    @Column(name = "matching_room_leader_id_2")
    private Long matchingRoomLeaderId2;
    @Column(name = "matching_room_leader_nickname_2")
    private String matchingRoomLeaderNickname2;
    private String sports;
    private Long capacity;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="booking_id")
    private Booking booking;
    @Enumerated(value = EnumType.STRING)
    @Column(name="booking_status")
    private BookingStatus status; // default : BookingStatus.PLACED;

    public static BookingMatchingRoom of(List<Matching> matchings, GetMatchingRoomResponse matchingRoomResponse1, GetMatchingRoomResponse matchingRoomResponse2, Long teamId1, Long teamId2) {
        BookingMatchingRoom bookingMatchingRoom = new BookingMatchingRoom();
        bookingMatchingRoom.matchingRoomId1 = Long.valueOf(matchings.get(0).getMatchingRoomId());
        bookingMatchingRoom.matchingRoomName1 = matchingRoomResponse1.getMatchingRoomName();
        bookingMatchingRoom.matchingRoomTeamId1 = teamId1;
        bookingMatchingRoom.matchingRoomLeaderId1 = matchingRoomResponse1.getMatchingRoomLeaderId();
        bookingMatchingRoom.matchingRoomLeaderNickname1 = matchingRoomResponse1.getMatchingRoomLeaderNickname();
        bookingMatchingRoom.matchingRoomId2 =  Long.valueOf(matchings.get(1).getMatchingRoomId());
        bookingMatchingRoom.matchingRoomName2 = matchingRoomResponse2.getMatchingRoomName();
        bookingMatchingRoom.matchingRoomTeamId2 = teamId2;
        bookingMatchingRoom.matchingRoomLeaderId2 = matchingRoomResponse2.getMatchingRoomLeaderId();
        bookingMatchingRoom.matchingRoomLeaderNickname2 = matchingRoomResponse2.getMatchingRoomLeaderNickname();
        bookingMatchingRoom.sports = matchings.get(0).getSports();
        bookingMatchingRoom.capacity = matchings.get(0).getCapacity()*2;
        bookingMatchingRoom.status = BookingStatus.DEFAULT;
        return bookingMatchingRoom;

    }

    public void setBooking(Booking booking){
        this.booking = booking;
        booking.setBookingMatchingRoom(this);
    }

    public void addMatchingId(Long matchingId){
        this.matchingId = matchingId;
    }

    public void changeBookingStatusToPlaced(){
        this.status = BookingStatus.PLACED;
    }

    public void changeBookingStatusToComplete(){
        this.status = BookingStatus.COMPLETED;
    }

    public void changeBookingStatusToCancel(){
        this.status = BookingStatus.CANCELED;
    }





}
