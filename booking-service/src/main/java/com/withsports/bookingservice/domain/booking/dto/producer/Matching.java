package com.withsports.bookingservice.domain.booking.dto.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matching {
    private String matchingRoomId;
    private String sports;
    private Long sumRating;
    private Long capacity;
    private Long teamId;
}
