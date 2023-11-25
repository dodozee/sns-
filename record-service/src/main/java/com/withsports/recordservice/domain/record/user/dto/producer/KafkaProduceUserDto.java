package com.withsports.recordservice.domain.record.user.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceUserDto {
    private Long userId;
    private String nickname;
    private String email;


}
