package com.withsports.matchingservice.domain.matchingroom.dto;


import com.withsports.matchingservice.domain.matchingroom.entity.MatchingRoom;
import com.withsports.matchingservice.domain.matchingroom.entity.MatchingRoomStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchingRoomDto {
    private Long matchingRoomId; // 매칭방 아이디
    private String matchingRoomName; // 매칭방 이름
    private Long teamId; // 팀 아이디
    private String teamName; // 팀 이름
    private Long roomLeaderId; // 방장 아이디
    private String roomLeaderNickname; // 방장 닉네임
    private String sports; // 종목
    private String area; // 지역
    private Long capacity; // 최대 인원, 방이 가득 차야지 가능
    private Long userCount; // 현재 인원
    private Long sumRating; // 방 점수
    private boolean participateStatus; // 참석 상태 -> 이 매칭방에 참여중인지 따라 보여지는 화면이 다름
    private String status; // 방 상태 -> 기본 상태, 서칭 중 상태

    public MatchingRoomDto(MatchingRoom matchingRoom) {
        this.matchingRoomId = matchingRoom.getId();
        this.matchingRoomName = matchingRoom.getMatchingRoomName();
        this.roomLeaderId = matchingRoom.getRoomLeaderId();
        this.teamId = matchingRoom.getTeamId();
        this.teamName = matchingRoom.getTeamName();
        this.roomLeaderNickname = matchingRoom.getRoomLeaderNickname();
        this.sports = matchingRoom.getSports();
        this.area = matchingRoom.getArea();
        this.capacity = matchingRoom.getCapacity();
        this.userCount = matchingRoom.getUserCount();
        this.sumRating = matchingRoom.getSumRating();
        this.participateStatus = false;
        this.status = matchingRoom.getStatus().toString();
    }




    public static MatchingRoomDto of(Long matchingRoomId, String matchingRoomName, Long teamId, String teamName, Long roomLeaderId, String roomLeaderNickname, String sports, String area, Long capacity, Long userCount, Long sumRating, boolean participateStatus, String status) {
        return MatchingRoomDto.builder()
                .matchingRoomId(matchingRoomId)
                .matchingRoomName(matchingRoomName)
                .teamId(teamId)
                .teamName(teamName)
                .roomLeaderId(roomLeaderId)
                .roomLeaderNickname(roomLeaderNickname)
                .sports(sports)
                .area(area)
                .capacity(capacity)
                .userCount(userCount)
                .sumRating(sumRating)
                .participateStatus(participateStatus)
                .status(status)
                .build();
    }

    public static MatchingRoomDto of(MatchingRoom matchingRoom){
        return new MatchingRoomDto(matchingRoom);

    }
}
