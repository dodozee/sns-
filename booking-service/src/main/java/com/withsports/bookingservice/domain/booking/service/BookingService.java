package com.withsports.bookingservice.domain.booking.service;

import com.withsports.bookingservice.domain.booking.dto.*;
import com.withsports.bookingservice.domain.booking.dto.producer.Matching;

import java.util.List;

public interface BookingService {
    void initBooking(List<Matching> matchings);

    void saveMatchingId(Long matchingId, Long matchingRoomId);

    List<CheckAvailableBookingRoomDto> getAvailableBooking(Long userId);

    List<AvailableTimeDto> getAvailableBookingDate(SearchingTimeByDateAndStadiumIdDto searchingTimeByDateAndStadiumIdDto);

    void booking(Long roomLeaderId, Long matchingRoomId, DateAndTimeDto dateAndTimeDto);

    List<CompletedBookingDetailDto> getBookingScheduleByTeamId(Long teamId);
}
