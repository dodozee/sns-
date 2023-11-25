package com.withsports.bookingservice.domain.booking.service;


import com.withsports.bookingservice.domain.booking.dto.*;
import com.withsports.bookingservice.domain.booking.dto.producer.Matching;
import com.withsports.bookingservice.domain.booking.entity.Booking;
import com.withsports.bookingservice.domain.booking.entity.BookingMatchingRoom;
import com.withsports.bookingservice.domain.booking.exception.NotAvailableMatchingRoomBookingException;
import com.withsports.bookingservice.domain.booking.repository.BookingMatchingRoomRepository;
import com.withsports.bookingservice.domain.stadium.entity.AvailableTime;
import com.withsports.bookingservice.domain.stadium.repository.AvailableTimeRepository;
import com.withsports.bookingservice.domain.stadium.repository.StadiumRepository;
import com.withsports.bookingservice.global.client.matching.GetMatchingRoomResponse;
import com.withsports.bookingservice.global.client.matching.MatchingClient;
import com.withsports.bookingservice.global.client.team.GetTeamIdResponse;
import com.withsports.bookingservice.global.client.team.TeamClient;
import com.withsports.bookingservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class BookingServiceImpl implements BookingService{

    private final BookingMatchingRoomRepository bookingMatchingRoomRepository;
    private final StadiumRepository stadiumRepository;
    private final AvailableTimeRepository availableTimeRepository;
    private final MatchingClient matchingClient;
    private final TeamClient teamClient;


    @Transactional
    @Override
    public void initBooking(List<Matching> matchings) {
        GetMatchingRoomResponse matchingRoomResponse1 = matchingClient.getMatchingRoomByMatchingRoomId(matchings.get(0).getMatchingRoomId()).getData();
        GetMatchingRoomResponse matchingRoomResponse2 = matchingClient.getMatchingRoomByMatchingRoomId(matchings.get(1).getMatchingRoomId()).getData();
        Long teamId1 = teamClient.getTeamByLeaderIdAndSportsId(String.valueOf(matchingRoomResponse1.getMatchingRoomLeaderId()), matchings.get(0).getSports()).getData().getTeamId();
        Long teamId2 = teamClient.getTeamByLeaderIdAndSportsId(String.valueOf(matchingRoomResponse2.getMatchingRoomLeaderId()), matchings.get(1).getSports()).getData().getTeamId();

        bookingMatchingRoomRepository.save(BookingMatchingRoom.of(matchings, matchingRoomResponse1, matchingRoomResponse2, teamId1, teamId2));

    }

    @Transactional
    @Override
    public void saveMatchingId(Long matchingId, Long matchingRoomId) {
        BookingMatchingRoom bookingMatchingRoom = bookingMatchingRoomRepository.findByMatchingRoomId1(matchingRoomId);
        bookingMatchingRoom.addMatchingId(matchingId);
    }

    @Override
    public List<CheckAvailableBookingRoomDto> getAvailableBooking(Long leaderId) {
        //TODO matchingRoomLeaderId로 예약 가능한 방 조회
        List<BookingMatchingRoom> rooms = bookingMatchingRoomRepository.findByMatchingRoomLeaderId1OrAndMatchingRoomLeaderId2AndStatus(leaderId);

        if(rooms.isEmpty()){
            throw new NotAvailableMatchingRoomBookingException("예약 가능한 방이 없습니다.");
        }

        //TODO 예약 가능한 방이 있으면 예약 가능한 방 목록 반환
        return rooms.stream()
                .map(CheckAvailableBookingRoomDto::of)
                .toList();

    }

    @Override
    public List<AvailableTimeDto> getAvailableBookingDate(SearchingTimeByDateAndStadiumIdDto searchingTimeByDateAndStadiumIdDto) {
        Long stadiumId = searchingTimeByDateAndStadiumIdDto.getStadiumId();
        Integer year = searchingTimeByDateAndStadiumIdDto.getYear();
        Integer month = searchingTimeByDateAndStadiumIdDto.getMonth();
        Integer day = searchingTimeByDateAndStadiumIdDto.getDay();

        //TODO 예약 가능한 시간 조회
        List<AvailableTime> availableTimes = bookingMatchingRoomRepository.findByAvailableDateAndStadiumId(stadiumId, year, month, day);

        return availableTimes.stream()
                .map(AvailableTimeDto::of)
                .toList();

    }

    @Transactional
    @Override
    public void booking(Long roomLeaderId, Long matchingRoomId, DateAndTimeDto dateAndTimedto) {
        //TODO 예약 가능한 방인지 확인
        BookingMatchingRoom bookingMatchingRoom = bookingMatchingRoomRepository.findByMatchingRoomId1OrMatchingRoomId2(matchingRoomId)
                .orElseThrow(() -> new NotAvailableMatchingRoomBookingException("예약 가능한 방이 없습니다."));
        //TODO 먼저 Stadium 의 시간대와 capacity를 확인 -> 예약 상태로 바꾸고 Booking에도 예약 상태 바꾸기
        stadiumRepository.findByAvailableDateAndStadiumId(dateAndTimedto.getStadiumId(), dateAndTimedto.getYear(), dateAndTimedto.getMonth(), dateAndTimedto.getDay(), dateAndTimedto.getHour(), dateAndTimedto.getCapacity())
                .orElseThrow(() -> new NotAvailableMatchingRoomBookingException("예약 가능한 방이나 시간대가 없습니다."));

        AvailableTime availableTime = availableTimeRepository.findByAvailableDateAndHourAndCapacity(dateAndTimedto.getStadiumId(), dateAndTimedto.getYear(), dateAndTimedto.getMonth(), dateAndTimedto.getDay(), dateAndTimedto.getHour(), dateAndTimedto.getCapacity())
                .orElseThrow(() -> new NotAvailableMatchingRoomBookingException("예약 가능한 방이나 시간대가 없습니다."));
        availableTime.changeBookingStatus(); //예약 마감처리

        Booking booking = Booking.createBooking(dateAndTimedto);
        bookingMatchingRoom.setBooking(booking);
        bookingMatchingRoom.changeBookingStatusToComplete();



    }

    @Override
    public List<CompletedBookingDetailDto> getBookingScheduleByTeamId(Long teamId) {
        //TODO 팀 아이디로 예약 일정 조회
        List<BookingMatchingRoom> bookingMatchingRooms = bookingMatchingRoomRepository.findByTeamId(teamId);
        return bookingMatchingRooms.stream()
                .map(CompletedBookingDetailDto::of)
                .toList();
    }
}
