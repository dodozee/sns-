package com.withsports.matchingservice.domain.matching.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProducePutMatchingPoolDto {
    private String sports;
    private String matchingRoomId;
    private Long sumRating;
    private Long capacity;

}
