package com.withsports.matchingservice.domain.matchingroom.dto.produce;

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



}
