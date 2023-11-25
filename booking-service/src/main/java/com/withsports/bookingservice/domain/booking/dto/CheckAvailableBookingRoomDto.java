package com.withsports.bookingservice.domain.booking.dto;


import com.withsports.bookingservice.domain.booking.entity.BookingMatchingRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckAvailableBookingRoomDto {
    private Long matchingId;
    private Long matchingRoomId1;
    private String matchingRoomName1;
    private Long matchingRoomId2;
    private String matchingRoomName2;
    private Long matchingRoomLeaderId1;
    private String matchingRoomLeaderNickname1;
    private Long matchingRoomLeaderId2;
    private String matchingRoomLeaderNickname2;
    private Long capacity;


    public static CheckAvailableBookingRoomDto of(BookingMatchingRoom bookingMatchingRoom) {
        return CheckAvailableBookingRoomDto.builder()
                .matchingId(bookingMatchingRoom.getMatchingId())
                .matchingRoomId1(bookingMatchingRoom.getMatchingRoomId1())
                .matchingRoomName1(bookingMatchingRoom.getMatchingRoomName1())
                .matchingRoomId2(bookingMatchingRoom.getMatchingRoomId2())
                .matchingRoomName2(bookingMatchingRoom.getMatchingRoomName2())
                .matchingRoomLeaderId1(bookingMatchingRoom.getMatchingRoomLeaderId1())
                .matchingRoomLeaderNickname1(bookingMatchingRoom.getMatchingRoomLeaderNickname1())
                .matchingRoomLeaderId2(bookingMatchingRoom.getMatchingRoomLeaderId2())
                .matchingRoomLeaderNickname2(bookingMatchingRoom.getMatchingRoomLeaderNickname2())
                .capacity(bookingMatchingRoom.getCapacity())
                .build();

    }
}
