package com.withsports.matchingservice.domain.matching.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.withsports.matchingservice.domain.matching.dto.EndGameResultDto;
import com.withsports.matchingservice.domain.matching.dto.MatchingDto;
import com.withsports.matchingservice.domain.matching.dto.MatchingRoomStatusDto;
import com.withsports.matchingservice.domain.matching.entity.MatchingStatus;
import com.withsports.matchingservice.domain.matching.exception.NotExactSportsException;
import com.withsports.matchingservice.domain.matching.redis.Matching;
import com.withsports.matchingservice.domain.matching.repository.MatchingRepository;
import com.withsports.matchingservice.domain.matchingroom.dto.produce.KafkaProduceBookingCompletedDto;
import com.withsports.matchingservice.domain.matchingroom.dto.produce.KafkaProduceUpdateUserProfileDto;
import com.withsports.matchingservice.domain.matchingroom.entity.MatchingRoom;
import com.withsports.matchingservice.domain.matchingroom.entity.RoomUser;
import com.withsports.matchingservice.domain.matchingroom.exception.NotExistMatchingRoomException;
import com.withsports.matchingservice.domain.matchingroom.repository.MatchingRoomRepository;
import com.withsports.matchingservice.domain.matchingroom.repository.RoomUserRepository;
import com.withsports.matchingservice.global.client.team.GetTeamResponse;
import com.withsports.matchingservice.global.client.team.TeamUserClient;
import com.withsports.matchingservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService{

    private final MatchingPoolService matchingPoolService;
    private final MatchingRoomRepository matchingRoomRepository;
    private final RoomUserRepository roomUserRepository;
    private final MatchingRepository matchingRepository;
    private final MatchingProducer matchingProducer;
    private final TeamUserClient teamUserClient;

    @Transactional
    @Override
    public void startMatching(Long matchingRoomId, Long roomLeaderId)  {
        //TODO roomLeaderId가 매칭방의 방장인지 확인
        //TODO 점수는 승/패 100점 씩 등락 비길시 없음
        MatchingRoom matchingRoom = matchingRoomRepository.findById(matchingRoomId).orElseThrow(() -> new IllegalArgumentException("해당 매칭방이 존재하지 않습니다."));
        Long capacity = matchingRoom.getCapacity();
        System.out.println("capacity = " + capacity);
        Long sumRating = matchingRoom.getSumRating();
        System.out.println("sumRating = " + sumRating);
        String sports = matchingRoom.getSports();
        System.out.println("sports = " + sports);
        MatchingDto matchingDto = new MatchingDto(String.valueOf(matchingRoomId), translateString(sports), sumRating, capacity, matchingRoom.getTeamId());
        matchingPoolService.addMatchingRating(matchingDto);
        //TODO 매칭 서칭 시작하면 매칭 서칭중으로 바꿔줘야함

        matchingRoom.changeMatchingSearchingStatus();
        notifyMatchingPoolScheduler(matchingDto);

        //TODO 강한 결합을 끊어주기 위해 카프카랑 스케쥴러를 통해 매칭을 찾음
        //producer -> 보내고 -> consumer 에서 받아서 -> scheduler로 주기적으로 매칭을 하고 있음 -> 매칭이 되면 알림을 보내줌
        /*
        List<Matching> matchings = matchingPool(matchingDto);

        notifyMatchingUsers(matchings);
        */

    }

    @Override
    public void cancelMatching(Long matchingRoomId, Long roomLeaderId) throws JsonProcessingException {
        List<Long> userIds = roomUserRepository.findAllUserIdByMatchingRoomId(matchingRoomId);

        //TODO roomLeaderId가 매칭방의 방장인지 확인
        //TODO 매칭 서칭중이면 매칭 취소으로 바꿔줘야함
        MatchingRoom matchingRoom = matchingRoomRepository.findById(matchingRoomId).orElseThrow(() -> new IllegalArgumentException("해당 매칭방이 존재하지 않습니다."));
        matchingRoom.changeMatchingCancelStatus();
        matchingPoolService.deleteMatching(matchingRoomId);
        matchingProducer.cancelMatching(userIds, matchingRoom.getMatchingRoomName());
//        matchingRoomRepository.delete(matchingRoom);

    }

    //TODO 알림이 2개씩 날라가는지 1개씩 날라가는지 확인해야함 *************************************
    @Transactional
    @Override
    public void notifyMatchingUsers(List<Matching> matchings) throws Exception {
        Matching matching1 = matchings.get(0);
        Matching matching2 = matchings.get(1);

        String matchingRoomId1 = matching1.getMatchingRoomId();
        String matchingRoomId2 = matching2.getMatchingRoomId();

        List<Long> userIds1 = roomUserRepository.findAllUserIdByMatchingRoomId(Long.parseLong(matchingRoomId1));
        List<Long> userIds2 = roomUserRepository.findAllUserIdByMatchingRoomId(Long.parseLong(matchingRoomId2));
        //teamName 알기
        MatchingRoom matchingRoom1 = matchingRoomRepository.findById(Long.parseLong(matchingRoomId1)).orElseThrow(() -> new IllegalArgumentException("해당 매칭방이 존재하지 않습니다."));
        String myTeamName = matchingRoom1.getTeamName();
        MatchingRoom matchingRoom2 = matchingRoomRepository.findById(Long.parseLong(matchingRoomId2)).orElseThrow(() -> new IllegalArgumentException("해당 매칭방이 존재하지 않습니다."));
        String yourTeamName = matchingRoom2.getTeamName();

        System.out.println("================매칭 성공했다는 알림 보내기 직전 ===================");
        //매칭 성공시 notification-service에 알림을 보내야함
        matchingProducer.successMatching(userIds1, myTeamName, yourTeamName);
        matchingProducer.successMatching(userIds2, yourTeamName, myTeamName);
        System.out.println("================매칭 성공했다는 알림 보내기 직후 ===================");
    }

    @Transactional
    @Override
    public void changeMatchingSuccessStatus(List<Matching> matchings) {
        Long roomId1 = Long.valueOf(matchings.get(0).getMatchingRoomId());
        Long roomId2 = Long.valueOf(matchings.get(1).getMatchingRoomId());
        matchingRoomRepository.findById(roomId1).orElseThrow(() -> new NotExistMatchingRoomException("해당 매칭방이 존재하지 않습니다."))
                .changeMatchingSuccessStatus();
        matchingRoomRepository.findById(roomId2).orElseThrow(() -> new NotExistMatchingRoomException("해당 매칭방이 존재하지 않습니다."))
                .changeMatchingSuccessStatus();
    }


    @Transactional
    @Override
    public void startGame(Long matchingRoomId, Long roomLeaderId) throws Exception {
        //TODO 경기가 잡혀있는지 확인 로직 -> 안잡혀있으면 Exception
        //TODO
        MatchingRoom matchingRoom = matchingRoomRepository.findById(matchingRoomId).orElseThrow(() -> new NotExistMatchingRoomException("해당 매칭방이 존재하지 않습니다."));
        matchingRoom.changeGameStartStatus();
        List<Long> userIds = roomUserRepository.findAllUserIdByMatchingRoomId(matchingRoomId);
        matchingProducer.alertStartGame(userIds);


    }

    //TODO : 경기 종료 - 경기 결과(승/무/패,레이팅) 저장및 승리팀 점수 부여, 패배팀 점수 차감(record service) 포인트 발생, 매칭방 상태변경, 알림 발생
    @Transactional
    @Override
    public void endGame(EndGameResultDto endGameResultDto) throws Exception {
        MatchingRoom matchingRoom = matchingRoomRepository.findById(endGameResultDto.getMatchingRoomId()).orElseThrow(() -> new NotExistMatchingRoomException("해당 매칭방이 존재하지 않습니다."));
        Long matchingRoomId1 = endGameResultDto.getMatchingRoomId(); //요청 보낸 매칭방의 id
        //TODO matchingRoomId1으로 Mathcing찾기
        Optional<com.withsports.matchingservice.domain.matching.entity.Matching> matching = matchingRepository.findByMatchingRoomId1(matchingRoomId1);

        if(matching.isPresent()){
            Result<GetTeamResponse> oppTeam = teamUserClient.getTeam(String.valueOf(matching.get().getTeamId2()));
            matchingRoom.changeGameEndStatus();
            List<Long> userIds = roomUserRepository.findAllUserIdByMatchingRoomId(endGameResultDto.getMatchingRoomId());
//        Result<GetTeamResponse> opponentTeamName = teamUserClient.getTeam(String.valueOf(oppTeam.getOpponentTeamId()));
            if (endGameResultDto.getResult().equals("승리")) {
                matchingProducer.alertEndGame(userIds, matchingRoom.getTeamName(), oppTeam.getData().getTeamName());
                matchingProducer.winGame(userIds, matchingRoom.getSports(), endGameResultDto.getMatchingRoomId(), matchingRoom.getTeamId(), matchingRoom.getTeamName(),matching.get().getTeamId2(), oppTeam.getData().getTeamName(), endGameResultDto.getResult(), endGameResultDto.getScore(), endGameResultDto.getOpponentScore(), matchingRoom.getArea());
            } else if(endGameResultDto.getResult().equals("패배")){
                matchingProducer.alertEndGame(userIds, oppTeam.getData().getTeamName(), matchingRoom.getTeamName());
                matchingProducer.loseGame(userIds, matchingRoom.getSports(), endGameResultDto.getMatchingRoomId(), matchingRoom.getTeamId(), matchingRoom.getTeamName(),matching.get().getTeamId2(), oppTeam.getData().getTeamName(), endGameResultDto.getResult(), endGameResultDto.getScore(), endGameResultDto.getOpponentScore(), matchingRoom.getArea());
            } else{
                matchingProducer.alertEndGameDraw(userIds, matchingRoom.getTeamName(), oppTeam.getData().getTeamName());
                matchingProducer.drawGame(userIds, matchingRoom.getSports(), endGameResultDto.getMatchingRoomId(), matchingRoom.getTeamId(), matchingRoom.getTeamName(),matching.get().getTeamId2(), oppTeam.getData().getTeamName(), endGameResultDto.getResult(), endGameResultDto.getScore(), endGameResultDto.getOpponentScore(), matchingRoom.getArea());
            }
        }
        else if(matching.isEmpty()){
            matching = matchingRepository.findByMatchingRoomId2(matchingRoomId1);
            if(matching.isEmpty()){
                throw new NotExistMatchingRoomException("해당 매칭이 존재하지 않습니다.");
            }
            Result<GetTeamResponse> oppTeam = teamUserClient.getTeam(String.valueOf(matching.get().getTeamId1()));
            matchingRoom.changeGameEndStatus();
            List<Long> userIds = roomUserRepository.findAllUserIdByMatchingRoomId(endGameResultDto.getMatchingRoomId());
//        Result<GetTeamResponse> opponentTeamName = teamUserClient.getTeam(String.valueOf(oppTeam.getOpponentTeamId()));
            if (endGameResultDto.getResult().equals("승리")) {
                matchingProducer.alertEndGame(userIds, matchingRoom.getTeamName(), oppTeam.getData().getTeamName());
                matchingProducer.winGame(userIds, matchingRoom.getSports(), endGameResultDto.getMatchingRoomId(), matchingRoom.getTeamId(), matchingRoom.getTeamName(),matching.get().getTeamId1(), oppTeam.getData().getTeamName(), endGameResultDto.getResult(), endGameResultDto.getScore(), endGameResultDto.getOpponentScore(), matchingRoom.getArea());
            } else if(endGameResultDto.getResult().equals("패배")){
                matchingProducer.alertEndGame(userIds, oppTeam.getData().getTeamName(), matchingRoom.getTeamName());
                matchingProducer.loseGame(userIds, matchingRoom.getSports(), endGameResultDto.getMatchingRoomId(), matchingRoom.getTeamId(), matchingRoom.getTeamName(),matching.get().getTeamId1(), oppTeam.getData().getTeamName(), endGameResultDto.getResult(), endGameResultDto.getScore(), endGameResultDto.getOpponentScore(), matchingRoom.getArea());
            } else{
                matchingProducer.alertEndGameDraw(userIds, matchingRoom.getTeamName(), oppTeam.getData().getTeamName());
                matchingProducer.drawGame(userIds, matchingRoom.getSports(), endGameResultDto.getMatchingRoomId(), matchingRoom.getTeamId(), matchingRoom.getTeamName(),matching.get().getTeamId1(), oppTeam.getData().getTeamName(), endGameResultDto.getResult(), endGameResultDto.getScore(), endGameResultDto.getOpponentScore(), matchingRoom.getArea());
            }
        }
        //TODO 상대팀 이름 알기 -> record-service에 저장하기 위해


    }

    @Transactional
    @Override
    public void saveMatchingRooms(List<Matching> matchings) {
        com.withsports.matchingservice.domain.matching.entity.Matching matching = new com.withsports.matchingservice.domain.matching.entity.Matching(matchings);
        com.withsports.matchingservice.domain.matching.entity.Matching save = matchingRepository.save(matching);
        save.changeMatchingStatusToBeforeBooking();
    }

    @Override
    public Long getMatchingPoolCount() {
        return matchingPoolService.getMatchingPoolCount();
    }

    @Override
    public MatchingRoomStatusDto getMatchingRoomsStatus() {
        Long beforeBooking = matchingRepository.countByMatchingStatus(MatchingStatus.BEFORE_BOOKING);
        Long doneBooking = matchingRepository.countByMatchingStatus(MatchingStatus.AFTER_BOOKING);
        Long startGame = matchingRepository.countByMatchingStatus(MatchingStatus.STARTED);
        Long endGame = matchingRepository.countByMatchingStatus(MatchingStatus.FINISHED);
        return new MatchingRoomStatusDto(beforeBooking, doneBooking, startGame, endGame);
    }

    @Transactional
    @Override
    public void updatedUserForMatching(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto) {
        List<RoomUser> byUserId = roomUserRepository.findByUserId(kafkaProduceUpdateUserProfileDto.getUserId());
        //TODo User의 nickname 변경
        if(!byUserId.isEmpty()) {
            for (RoomUser roomUser : byUserId) {
                roomUser.changeNickname(kafkaProduceUpdateUserProfileDto.getNickname());
            }
        }
        matchingRoomRepository.findByRoomLeaderId(kafkaProduceUpdateUserProfileDto.getUserId())
                .ifPresent(matchingRoom -> matchingRoom.changeRoomLeaderNickname(kafkaProduceUpdateUserProfileDto.getNickname()));
    }


    @Transactional
    @Override
    public void bookingCompleted(KafkaProduceBookingCompletedDto kafkaProduceBookingCompletedDto) throws JsonProcessingException {
        //TODO 매칭방의 상태를 예약완료로 변경
        matchingRoomRepository.findById(kafkaProduceBookingCompletedDto.getMatchingRoomId1())
                .ifPresent(MatchingRoom::changeBookingCompletedStatus);
        matchingRoomRepository.findById(kafkaProduceBookingCompletedDto.getMatchingRoomId2())
                .ifPresent(MatchingRoom::changeBookingCompletedStatus);

        matchingRepository.findById(kafkaProduceBookingCompletedDto.getMatchingId())
                .ifPresent(com.withsports.matchingservice.domain.matching.entity.Matching::changeMatchingStatusToAfterBooking);
        List<Long> allUserIdByMatchingRoomId1 = roomUserRepository.findAllUserIdByMatchingRoomId(kafkaProduceBookingCompletedDto.getMatchingRoomId1());
        List<Long> allUserIdByMatchingRoomId2 = roomUserRepository.findAllUserIdByMatchingRoomId(kafkaProduceBookingCompletedDto.getMatchingRoomId2());
        String matchingRoomName1 = kafkaProduceBookingCompletedDto.getMatchingRoomName1();
        String matchingRoomName2 = kafkaProduceBookingCompletedDto.getMatchingRoomName2();
        matchingProducer.alertBookingCompleted(allUserIdByMatchingRoomId1,allUserIdByMatchingRoomId2,matchingRoomName1,matchingRoomName2,kafkaProduceBookingCompletedDto.getDescription());
    }

    @Override
    public boolean isMatched(Long matchingRoomId, Long roomLeaderId) {
        Optional<com.withsports.matchingservice.domain.matching.entity.Matching> matching = matchingRepository.findByMatchingRoomId1OrMatchingRoomId2(matchingRoomId);
        if(matching.isPresent()){
            return true;
        }
        return false;
    }

    //TODO 예약 성공시 매칭방 상태 변경(kafka로 알림)

    public void notifyMatchingPoolScheduler(MatchingDto matchingDto) {
        switch (matchingDto.getSports()) {
            case "축구" -> {
                System.out.println("축구 매칭풀 들어감");
            }
            case "농구" -> {
                System.out.println("농구 매칭풀 들어감");
            }
            case "풋살" -> System.out.println("풋살 매칭풀 들어감");

            default -> throw new NotExactSportsException("해당하는 종목이 없습니다.");

        }

    }

    public String translateString(String sports) {
        return switch (sports) {
            case "풋살" -> "풋살";
            case "축구" -> "축구";
            case "농구" -> "농구";
            default -> "Invalid input";
        };
    }

}
