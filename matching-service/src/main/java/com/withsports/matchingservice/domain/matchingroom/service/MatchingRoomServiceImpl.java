package com.withsports.matchingservice.domain.matchingroom.service;

import com.withsports.matchingservice.domain.matchingroom.dto.CreateMatchingRoomDto;
import com.withsports.matchingservice.domain.matchingroom.dto.MatchingRoomDto;
import com.withsports.matchingservice.domain.matchingroom.entity.MatchingRoom;
import com.withsports.matchingservice.domain.matchingroom.entity.Role;
import com.withsports.matchingservice.domain.matchingroom.entity.RoomUser;
import com.withsports.matchingservice.domain.matchingroom.exception.*;
import com.withsports.matchingservice.domain.matchingroom.repository.MatchingRoomRepository;
import com.withsports.matchingservice.domain.matchingroom.repository.RoomUserRepository;
import com.withsports.matchingservice.global.client.record.GetUserRecordResponse;
import com.withsports.matchingservice.global.client.record.RecordClient;
import com.withsports.matchingservice.global.client.team.GetTeamIdsResponse;
import com.withsports.matchingservice.global.client.team.GetTeamResponse;
import com.withsports.matchingservice.global.client.team.TeamUserClient;
import com.withsports.matchingservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingRoomServiceImpl implements MatchingRoomService {


    private final MatchingRoomRepository matchingRoomRepository;
    private final RoomUserRepository roomUserRepository;
    private final TeamUserClient teamUserClient;
    private final RecordClient recordClient;
    private final MatchingRoomProducer matchingRoomProducer;
    @Transactional
    @Override
    public Long createMatchingRoom(Long roomLeaderId, CreateMatchingRoomDto createMatchingRoomDto) {

        Result<GetTeamResponse> teamResponse = teamUserClient.getTeamIdByUserIdAndSports(String.valueOf(roomLeaderId), createMatchingRoomDto.getSports());
        //TODO 리더 이름 가져오기
        if(teamResponse.getData().getTeamId()==null){
            throw new NotExistTeamException("팀이 존재하지 않습니다.");
        }
        Result<GetUserRecordResponse> recordResponse = recordClient.getRecordByUserId(String.valueOf(roomLeaderId), createMatchingRoomDto.getSports());
        Long teamId = teamResponse.getData().getTeamId();
        System.out.println("teamId = " + teamId);
        String teamName = teamResponse.getData().getTeamName();
        System.out.println("teamName = " + teamName);
        String title = createMatchingRoomDto.getTitle();
        System.out.println("title = " + title);
        Long rating = recordResponse.getData().getRating();
        String nickname = recordResponse.getData().getNickname();
        System.out.println("rating = " + rating);

        MatchingRoom matchingRoom = MatchingRoom.createMatchingRoom(roomLeaderId, nickname, title, teamId, teamName, rating, createMatchingRoomDto);

        System.out.println("matchingRoom.getRoomLeaderId() = " + matchingRoom.getRoomLeaderId());
        //TODO : 매칭방 생성시 방장의 기록을 가져와서 매칭방의 점수로 넣어줘야함(여기까지 하다가 말음)
        MatchingRoom room = matchingRoomRepository.save(matchingRoom);
        System.out.println("room.getId() = " + room.getId());
//        room.addSumRating(recordResponse.getData().getRating());
        RoomUser roomUser = RoomUser.of(roomLeaderId, teamResponse.getData().getTeamId(), nickname, Role.LEADER, recordResponse.getData().getRating(), recordResponse.getData().getTier());

        room.addRoomUser(roomUser);
        roomUserRepository.save(roomUser);
        roomUser.setMatchingRoom(room);

        return room.getId();

    }

    //수정이 심각하게 필요함
    @Override
    public List<MatchingRoomDto> findMatchingRoomList(Long userId) {
        //유저가 포함된 팀의 id들 나옴
        GetTeamIdsResponse response = teamUserClient.getTeamIdByUserId(String.valueOf(userId)).getData();
        //반환할 매칭방 리스트 선언
        List<MatchingRoomDto> matchingRoomListResponse = new ArrayList<>();

        List<Long> teamIdTests = response.getTeamIds();

        //팀 아이디 리스트 그냥 출력
        for (Long teamId : teamIdTests) {
            System.out.println("teamId = " + teamId);
        }

        List<Long> teamIds = response.getTeamIds();
        int size = teamIds.size();
        for(int i = 0; i < size; i++){
            System.out.println("teamIdList.get(i) = " + teamIds.get(i));
            //팀 아이디로 매칭방 리스트 가져옴(1개 이상)
            List<MatchingRoom> matchingRooms = matchingRoomRepository.findMatchingRoomByTeamId(teamIds.get(i));
            for(MatchingRoom matchingRoom : matchingRooms){
                //매칭방에 유저가 포함되어있는지 확인 있으면 -> 매칭방에 참여중인 상태, 없으면 -> 매칭방에 참여중이지 않은 상태
                Optional<RoomUser> roomUser = roomUserRepository.findByUserIdAndMatchingRoomId(userId, matchingRoom.getId());//teamIdList.get(i)
                //roomUser가 null이면 매칭방에 참여중이지 않은 상태
                if(roomUser.isEmpty()){
                    MatchingRoomDto matchingRoomDto = MatchingRoomDto.of(matchingRoom.getId(), matchingRoom.getMatchingRoomName(), matchingRoom.getTeamId(), matchingRoom.getTeamName(), matchingRoom.getRoomLeaderId(),matchingRoom.getRoomLeaderNickname(), matchingRoom.getSports(), matchingRoom.getArea(), matchingRoom.getCapacity(), matchingRoom.getUserCount(), matchingRoom.getSumRating(),false, matchingRoom.getStatus().getMessage());
                    matchingRoomListResponse.add(matchingRoomDto);
                    continue;
                }
                MatchingRoomDto matchingRoomDto = MatchingRoomDto.of(matchingRoom.getId(), matchingRoom.getMatchingRoomName(), matchingRoom.getTeamId(), matchingRoom.getTeamName(),matchingRoom.getRoomLeaderId(), matchingRoom.getRoomLeaderNickname(), matchingRoom.getSports(), matchingRoom.getArea(), matchingRoom.getCapacity(), matchingRoom.getUserCount(), matchingRoom.getSumRating(), roomUser.get().getParticipateStatus(), matchingRoom.getStatus().getMessage());
                matchingRoomListResponse.add(matchingRoomDto);
            }
            /**
             * private Long matchingRoomId; // 매칭방 아이디
             *     private Long teamId; // 팀 아이디
             *     private String teamName; // 팀 이름
             *     private String roomLeaderNickname; // 방장 닉네임
             *     private String sports; // 종목
             *     private String area; // 지역
             *     private Long capacity; // 최대 인원, 방이 가득 차야지 가능
             *     private Long userCount; // 현재 인원
             *     private Long sumScore; // 방 점수
             *     private boolean participateStatus; // 참석 상태 -> 이 매칭방에 참여중인지 따라 보여지는 화면이 다름
             *     private String status; // 방 상태 -> 기본 상태, 서칭 중 상태
             */
        }

        return matchingRoomListResponse;
    }

    @Transactional
    @Override
    public void deleteMatchingRoom(Long matchingRoomId, Long roomLeaderId) throws Exception {
        Optional<MatchingRoom> matchingRoom = matchingRoomRepository.findById(matchingRoomId);
        if(matchingRoom.isEmpty()){
            throw new IllegalArgumentException("존재하지 않는 매칭방입니다.");
        }
        if(!matchingRoom.get().getRoomLeaderId().equals(roomLeaderId)){
            throw new IllegalArgumentException("방장만 매칭방을 삭제할 수 있습니다.");
        }
        List<RoomUser> roomUsers = matchingRoom.get().getRoomUsers();

        List<Long> userIdList = new ArrayList<>();
        for(RoomUser roomUser : roomUsers){
            userIdList.add(roomUser.getUserId());
        }

        matchingRoomProducer.deletedMatchingRoom(userIdList, matchingRoom.get().getMatchingRoomName());
        matchingRoomRepository.delete(matchingRoom.get());

    }

    @Transactional
    @Override
    public void enterMatchingRoom(Long userId, Long matchingRoomId) throws Exception {
        MatchingRoom matchingRoom = matchingRoomRepository.findById(matchingRoomId)
                .orElseThrow(() -> new NotExistMatchingRoomException("존재하지 않는 매칭방입니다."));

        if(matchingRoom.getUserCount().equals(matchingRoom.getCapacity())){
            throw new FullMatchingRoomException("매칭방이 가득 찼습니다.");
        }
        //만약 이미 참여한 매칭방이면
        if(roomUserRepository.findByUserIdAndMatchingRoomId(userId, matchingRoomId).isPresent()){
            throw new AlreadyParticipationException("이미 참여한 매칭방입니다.");
        }

        Result<GetUserRecordResponse> recordResponse = recordClient.getRecordByUserId(String.valueOf(userId), matchingRoom.getSports());
        RoomUser roomUser = RoomUser.of(userId, matchingRoom.getTeamId(), recordResponse.getData().getNickname(), Role.NORMAL, recordResponse.getData().getRating(), recordResponse.getData().getTier());
        matchingRoom.addRoomUser(roomUser);
        roomUserRepository.save(roomUser);
        roomUser.setMatchingRoom(matchingRoom);
        matchingRoom.addUserCount();
        matchingRoom.addSumRating(recordResponse.getData().getRating());

        //방이 꽉차면 방장한테 알림 보내기
        if(matchingRoom.getUserCount().equals(matchingRoom.getCapacity())){
            matchingRoomProducer.fullMatchingRoom(matchingRoom.getRoomLeaderId(), matchingRoom.getMatchingRoomName(), matchingRoom.getCapacity());
        }

    }

    @Transactional
    @Override
    public void exitMatchingRoom(Long userId, Long matchingRoomId) {
        MatchingRoom matchingRoom = matchingRoomRepository.findById(matchingRoomId)
                .orElseThrow(() -> new NotExistMatchingRoomException("존재하지 않는 매칭방입니다."));

        RoomUser roomUser = roomUserRepository.findByUserIdAndMatchingRoomId(userId, matchingRoomId)
                .orElseThrow(() -> new NotExistUserException("존재하지 않는 유저입니다."));

        matchingRoom.removeRoomUser(roomUser);
        roomUserRepository.delete(roomUser);
    }

    @Override
    public MatchingRoomDto findMatchingRoomById(Long matchingRoomId) {
        MatchingRoom matchingRoom = matchingRoomRepository.findById(matchingRoomId)
                .orElseThrow(() -> new NotExistMatchingRoomException("존재하지 않는 매칭방입니다."));
        return MatchingRoomDto.of(matchingRoom);

    }
}
