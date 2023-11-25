package com.withsports.bookingservice.domain.booking.dto.producer;

import com.withsports.bookingservice.domain.booking.entity.BookingMatchingRoom;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class KafkaProduceBookingCompletedDto {

    private Long matchingId;
    private Long matchingRoomId1;
    private String matchingRoomName1;
    private Long matchingRoomLeaderId1;
    private String matchingRoomLeaderNickname1;
    private Long matchingRoomId2;
    private String matchingRoomName2;
    private Long matchingRoomLeaderId2;
    private String matchingRoomLeaderNickname2;
    private String description;


    public static KafkaProduceBookingCompletedDto of(BookingMatchingRoom bookingMatchingRoom, String description) {
        return new KafkaProduceBookingCompletedDto(
                bookingMatchingRoom.getMatchingId(),
                bookingMatchingRoom.getMatchingRoomId1(),
                bookingMatchingRoom.getMatchingRoomName1(),
                bookingMatchingRoom.getMatchingRoomLeaderId1(),
                bookingMatchingRoom.getMatchingRoomLeaderNickname1(),
                bookingMatchingRoom.getMatchingRoomId2(),
                bookingMatchingRoom.getMatchingRoomName2(),
                bookingMatchingRoom.getMatchingRoomLeaderId2(),
                bookingMatchingRoom.getMatchingRoomLeaderNickname2()
                ,description
        );
    }
}
