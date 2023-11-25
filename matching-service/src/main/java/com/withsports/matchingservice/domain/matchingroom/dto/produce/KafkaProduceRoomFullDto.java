package com.withsports.matchingservice.domain.matchingroom.dto.produce;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceRoomFullDto {
    private Long roomLeaderId;
    private String title;
    private Long capacity;

}
