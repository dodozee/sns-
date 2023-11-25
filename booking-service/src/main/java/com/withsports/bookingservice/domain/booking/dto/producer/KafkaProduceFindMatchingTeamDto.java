package com.withsports.bookingservice.domain.booking.dto.producer;


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
