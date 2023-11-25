package com.withsports.matchingservice.domain.matchingroom.entity;

import lombok.Getter;

@Getter
public enum MatchingRoomStatus {
    NONE("매칭방 없음"),
    WAITING("매칭방 인원 모집 중"),
    FULL("매칭방 인원 가득 참"),
    SEARCHING("매칭 탐색 중"),
    MATCHED("매칭 완료"),
    BOOKING("경기 예약 완료"),
    STARTED("경기 시작"),
    FINISHED("경기 종료"),
    FAILED("매칭 실패"),
    CANCELED("매칭 취소");

    private final String message;

    MatchingRoomStatus(String message) {
        this.message = message;
    }
}
