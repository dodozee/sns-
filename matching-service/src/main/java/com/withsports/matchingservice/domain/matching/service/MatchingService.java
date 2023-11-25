package com.withsports.matchingservice.domain.matching.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.withsports.matchingservice.domain.matching.dto.EndGameResultDto;
import com.withsports.matchingservice.domain.matching.dto.MatchingDto;
import com.withsports.matchingservice.domain.matching.dto.MatchingRoomStatusDto;
import com.withsports.matchingservice.domain.matching.redis.Matching;
import com.withsports.matchingservice.domain.matchingroom.dto.produce.KafkaProduceBookingCompletedDto;
import com.withsports.matchingservice.domain.matchingroom.dto.produce.KafkaProduceUpdateUserProfileDto;

import java.util.List;

public interface MatchingService {

    void startMatching(Long matchingRoomId, Long roomLeaderId);

    void cancelMatching(Long matchingRoomId, Long roomLeaderId) throws JsonProcessingException;

    void notifyMatchingPoolScheduler(MatchingDto matchingDto) throws InterruptedException, JsonProcessingException;

    void notifyMatchingUsers(List<Matching> matchings) throws Exception;

    void changeMatchingSuccessStatus(List<Matching> matchings);

    void startGame(Long matchingRoomId, Long roomLeaderId) throws Exception;

    void endGame(EndGameResultDto endGameResultDto) throws Exception;

    void saveMatchingRooms(List<Matching> matchings);

    Long getMatchingPoolCount();

    MatchingRoomStatusDto getMatchingRoomsStatus();

    void updatedUserForMatching(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto);

    void bookingCompleted(KafkaProduceBookingCompletedDto kafkaProduceBookingCompletedDto) throws JsonProcessingException;

    boolean isMatched(Long matchingRoomId, Long roomLeaderId);
}
