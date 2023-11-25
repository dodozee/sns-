package com.withsports.pointservice.domain.point.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaProduceBuyGifticonDto {
    private Long fromUserId;
    private String fromUserNickname;
    private Long toUserId;
    private String toUserNickname;
    private Long gifticonId;
    private String gifticonName;
    private Long gifticonPrice;

}
