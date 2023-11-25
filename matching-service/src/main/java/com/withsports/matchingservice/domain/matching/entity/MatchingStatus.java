package com.withsports.matchingservice.domain.matching.entity;

import lombok.Getter;

@Getter
public enum MatchingStatus {
    DEFAULT("디폴트"),
    BEFORE_BOOKING("예약대기"),
    AFTER_BOOKING("예약성공"),
    STARTED("경기 시작"),
    FINISHED("경기 종료");


    private final String message;

    MatchingStatus(String message){
        this.message = message;
    }
}
