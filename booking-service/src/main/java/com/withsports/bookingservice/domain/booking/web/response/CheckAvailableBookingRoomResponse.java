package com.withsports.bookingservice.domain.booking.web.response;

import com.withsports.bookingservice.domain.booking.dto.CheckAvailableBookingRoomDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckAvailableBookingRoomResponse {
    private Long matchingId; // 매칭 id
    private Long matchingRoomId1; // 매칭룸1 id
    private String matchingRoomName1; // 매칭룸 이름
    private Long matchingRoomId2; // 매칭룸1 id
    private String matchingRoomName2; // 매칭룸2 이름
    private Long matchingRoomLeaderId1; // 매칭룸1 방장 id
    private String matchingRoomLeaderNickname1; // 매칭룸1 방장 닉네임
    private Long matchingRoomLeaderId2; // 매칭룸2 방장 id
    private String matchingRoomLeaderNickname2; // 매칭룸2 방장 닉네임
    private Long capacity; // 매칭룸1,2 총 인원

    public CheckAvailableBookingRoomResponse(CheckAvailableBookingRoomDto checkAvailableBookingRoomDto) {
        this.matchingId = checkAvailableBookingRoomDto.getMatchingId();
        this.matchingRoomId1 = checkAvailableBookingRoomDto.getMatchingRoomId1();
        this.matchingRoomName1 = checkAvailableBookingRoomDto.getMatchingRoomName1();
        this.matchingRoomId2 = checkAvailableBookingRoomDto.getMatchingRoomId2();
        this.matchingRoomName2 = checkAvailableBookingRoomDto.getMatchingRoomName2();
        this.matchingRoomLeaderId1 = checkAvailableBookingRoomDto.getMatchingRoomLeaderId1();
        this.matchingRoomLeaderNickname1 = checkAvailableBookingRoomDto.getMatchingRoomLeaderNickname1();
        this.matchingRoomLeaderId2 = checkAvailableBookingRoomDto.getMatchingRoomLeaderId2();
        this.matchingRoomLeaderNickname2 = checkAvailableBookingRoomDto.getMatchingRoomLeaderNickname2();
        this.capacity = checkAvailableBookingRoomDto.getCapacity();
    }
}
