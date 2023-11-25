package com.withsports.bookingservice.domain.stadium.service;

import com.withsports.bookingservice.domain.booking.dto.RegisterStadiumDateDto;
import com.withsports.bookingservice.domain.stadium.dto.AvailableDateAndTimeAndCapacityDto;
import com.withsports.bookingservice.domain.stadium.dto.StadiumDto;
import com.withsports.bookingservice.domain.stadium.web.request.RegisterStadiumRequest;

import java.util.List;
import java.util.Map;

public interface StadiumService {
    void registerStadium(RegisterStadiumRequest registerStadiumRequest, String adminId);

    List<StadiumDto> findStadiumsByArea(String area);

    void registerStadiumDate(Long stadiumId, RegisterStadiumDateDto registerStadiumDateDto);

    List<AvailableDateAndTimeAndCapacityDto> findStadiumDateById(Long stadiumId);

    StadiumDto findStadiumById(Long stadiumId);
}
