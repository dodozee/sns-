package com.withsports.bookingservice.domain.stadium.service;

import com.withsports.bookingservice.domain.booking.dto.RegisterStadiumDateDto;
import com.withsports.bookingservice.domain.booking.exception.NotExistStadiumException;
import com.withsports.bookingservice.domain.stadium.dto.AvailableDateAndTimeAndCapacityDto;
import com.withsports.bookingservice.domain.stadium.dto.StadiumDto;
import com.withsports.bookingservice.domain.stadium.entity.AvailableDate;
import com.withsports.bookingservice.domain.stadium.entity.AvailableTime;
import com.withsports.bookingservice.domain.stadium.entity.Stadium;
import com.withsports.bookingservice.domain.stadium.repository.StadiumRepository;
import com.withsports.bookingservice.domain.stadium.web.request.RegisterStadiumRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StadiumServiceImpl implements StadiumService {

    private final StadiumRepository stadiumRepository;


    @Transactional
    @Override
    public void registerStadium(RegisterStadiumRequest registerStadiumRequest, String adminId) {
        Stadium stadium = Stadium.createStadium(registerStadiumRequest, adminId);
        stadiumRepository.save(stadium);
    }

    @Override
    public List<StadiumDto> findStadiumsByArea(String area) {
        List<Stadium> stadiums= stadiumRepository.findByAddressContaining(area);
        return stadiums.stream()
                .map(StadiumDto::of)
                .toList();
    }

    @Transactional
    @Override
    public void registerStadiumDate(Long stadiumId, RegisterStadiumDateDto registerStadiumDateDto) {
        Stadium stadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new NotExistStadiumException("존재하지 않는 구장입니다."));
        AvailableDate availableDate = stadium.registerStadiumDate(registerStadiumDateDto);
        AvailableTime availableTime = AvailableTime.createAvailableTime(registerStadiumDateDto);
        availableDate.addAvailableTime(availableTime);
    }

    @Override
    public List<AvailableDateAndTimeAndCapacityDto> findStadiumDateById(Long stadiumId) {
        //TODO 구장별 예약 가능 날짜&시간 조회
        return stadiumRepository.findStadiumDateAndTimeAndCapacityById(stadiumId);
    }

    @Override
    public StadiumDto findStadiumById(Long stadiumId) {
        Stadium stadium = stadiumRepository.findById(stadiumId)
                .orElseThrow(() -> new NotExistStadiumException("존재하지 않는 구장입니다."));
        return StadiumDto.of(stadium);
    }
}
