package com.withsports.matchingservice.domain.matching.dto.producer;


import com.withsports.matchingservice.domain.matching.redis.Matching;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KafkaProduceFindMatchingTeamDto {
    private List<Matching> matchingList;

    public List<Matching> getMatchings() {
        return matchingList;
    }
}
