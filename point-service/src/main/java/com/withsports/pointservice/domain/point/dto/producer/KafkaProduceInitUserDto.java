package com.withsports.pointservice.domain.point.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceInitUserDto {
        private Long userId;
        private String nickname;
}
