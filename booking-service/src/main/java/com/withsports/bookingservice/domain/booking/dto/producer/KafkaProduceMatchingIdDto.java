package com.withsports.bookingservice.domain.booking.dto.producer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceMatchingIdDto {
    private Long matchingId;
    private Long matchingRoomId1;
}
