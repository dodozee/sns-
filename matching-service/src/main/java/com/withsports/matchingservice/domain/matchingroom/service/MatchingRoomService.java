package com.withsports.matchingservice.domain.matchingroom.service;

import com.withsports.matchingservice.domain.matchingroom.dto.CreateMatchingRoomDto;
import com.withsports.matchingservice.domain.matchingroom.dto.MatchingRoomDto;

import java.util.List;

public interface MatchingRoomService {

    Long createMatchingRoom(Long roomLeaderId, CreateMatchingRoomDto createMatchingRoomDto);

    List<MatchingRoomDto> findMatchingRoomList(Long userId);

    void deleteMatchingRoom(Long matchingRoomId, Long roomLeaderId) throws Exception;

    void enterMatchingRoom(Long userId, Long matchingRoomId) throws Exception;

    void exitMatchingRoom(Long userId, Long matchingRoomId);

    MatchingRoomDto findMatchingRoomById(Long matchingRoomId);
}
