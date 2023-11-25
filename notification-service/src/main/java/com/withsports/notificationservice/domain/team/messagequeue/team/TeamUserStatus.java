package com.withsports.notificationservice.domain.team.messagequeue.team;

import lombok.Getter;

@Getter
public enum TeamUserStatus {
    NONE("가입 신청 없음"),
    PLACED("팀 가입 신청"),
    ACCEPTED("팀 가입 승인"),
    REJECTED("팀 가입 거절"),
    FAILED("팀 가입 실패");

    private final String message;

    TeamUserStatus(String message) {
        this.message = message;
    }
}
