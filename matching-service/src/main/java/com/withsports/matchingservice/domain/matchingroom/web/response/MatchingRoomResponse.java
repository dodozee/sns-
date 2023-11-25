package com.withsports.matchingservice.domain.matchingroom.web.response;

import com.withsports.matchingservice.domain.matchingroom.dto.MatchingRoomDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MatchingRoomResponse {
    private Long matchingRoomId; // 매칭방 id
    private String matchingRoomName; // 매칭방 이름
    private Long teamId; // 팀 아이디
    private String teamName; // 팀 이름
    private Long roomLeaderId; // 방장 아이디
    private String roomLeaderNickname; // 방장 닉네임
    private String sports; // 종목
    private String area; // 지역
    private Long capacity; // 최대 수용 인원, 방이 가득 차야지 매칭방 서칭 가능 -> 매칭방이 가득 차면 매칭방이 [꽉 참] 또는 [참여불가]
    private Long userCount; // 현재 인원
    private Long sumRating; // 방 레이팅 점수
    private boolean participateStatus; // 참석 상태 -> 이 매칭방에 참여중인지 따라 보여지는게 다르기 위한 용도 Ex) [참여하기],[참여중]
    private String status; // 방 상태 -> 기본 상태, 서칭 중 상태


    public MatchingRoomResponse(MatchingRoomDto roomDto){
        this.matchingRoomId = roomDto.getMatchingRoomId();
        this.matchingRoomName = roomDto.getMatchingRoomName();
        this.teamId = roomDto.getTeamId();
        this.teamName = roomDto.getTeamName();
        this.roomLeaderId = roomDto.getRoomLeaderId();
        this.roomLeaderNickname = roomDto.getRoomLeaderNickname();
        this.sports = roomDto.getSports();
        this.area = roomDto.getArea();
        this.capacity = roomDto.getCapacity();
        this.userCount = roomDto.getUserCount();
        this.sumRating = roomDto.getSumRating();
        this.participateStatus = roomDto.isParticipateStatus();
        this.status = roomDto.getStatus();
    }
}
