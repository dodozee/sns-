package com.withsports.matchingservice.domain.matching.dto.producer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceSearchingDto {
    private Long matchingRoomId;
    private Long roomLeaderId;
}
