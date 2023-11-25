package com.withsports.bookingservice.domain.booking.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DateRequest {
    private Integer year;
    private Integer month;
    private Integer day;

}
