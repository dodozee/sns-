package com.withsports.matchingservice.domain.matchingroom.dto.produce;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceRoomUserListDto {
    private String roomTitle;
    private List<Long> userIdList;


}
