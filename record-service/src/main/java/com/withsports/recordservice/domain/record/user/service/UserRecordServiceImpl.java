package com.withsports.recordservice.domain.record.user.service;

import com.withsports.recordservice.domain.record.matching.entity.MatchingRecord;
import com.withsports.recordservice.domain.record.matching.repository.MatchingRecordRepository;
import com.withsports.recordservice.domain.record.team.service.TeamRecordService;
import com.withsports.recordservice.domain.record.user.dto.UserRankingByRatingDto;
import com.withsports.recordservice.domain.record.user.dto.UserRecordDetailHistoryDto;
import com.withsports.recordservice.domain.record.user.dto.UserRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceDrawRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceUpdateUserProfileDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceUserDto;
import com.withsports.recordservice.domain.record.user.entity.Tier;
import com.withsports.recordservice.domain.record.user.entity.UserRecord;
import com.withsports.recordservice.domain.record.user.repository.UserRecordRepository;
import com.withsports.recordservice.domain.record.user.repository.UserRecordRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRecordServiceImpl implements UserRecordService{

    private final UserRecordRepository userRecordRepository;
    private final MatchingRecordRepository teamRecordRepository;
    private final TeamRecordService teamRecordService;
    private final UserRecordRepositoryCustom userRecordRepositoryCustom;

    @Override
    public UserRecordDto getRecordByUserId(Long userId, String sports) {
        UserRecord userRecord = userRecordRepository.findByUserIdAndSports(userId, sports);
        return UserRecordDto.builder()
                .userId(userRecord.getUserId())
                .nickname(userRecord.getNickname())
                .sports(userRecord.getSports())
                .win(userRecord.getWin())
                .draw(userRecord.getDraw())
                .lose(userRecord.getLose())
                .rating(userRecord.getRating())
                .tier(userRecord.getTier().name())
                .build();
    }

    @Transactional
    @Override
    public void initUserRecord(KafkaProduceUserDto kafkaProduceTeamDto) {
        //TODO userRecord에 해당 유저의 기록이 있는지 확인 이미 있으면 return
        if(userRecordRepository.findByUserIdAndSports(kafkaProduceTeamDto.getUserId(), "축구") != null){
            return ;
        }
        UserRecord userRecordS = initRecordS(kafkaProduceTeamDto);
        UserRecord userRecordF = initRecordF(kafkaProduceTeamDto);
        UserRecord userRecordB = initRecordB(kafkaProduceTeamDto);
        userRecordRepository.save(userRecordS);
        userRecordRepository.save(userRecordF);
        userRecordRepository.save(userRecordB);




    }

    private static UserRecord initRecordS(KafkaProduceUserDto kafkaProduceTeamDto) {
        UserRecord userRecord = UserRecord.builder()
                .userId(kafkaProduceTeamDto.getUserId())
                .nickname(kafkaProduceTeamDto.getNickname())
                .sports("축구")
                .win(0L)
                .draw(0L)
                .lose(0L)
                .rating(1000L)
                .tier(Tier.초보자)
                .build();

        return userRecord;
    }
    private static UserRecord initRecordF(KafkaProduceUserDto kafkaProduceTeamDto) {
        UserRecord userRecord = UserRecord.builder()
                .userId(kafkaProduceTeamDto.getUserId())
                .nickname(kafkaProduceTeamDto.getNickname())
                .sports("풋살")
                .win(0L)
                .draw(0L)
                .lose(0L)
                .rating(1000L)
                .tier(Tier.초보자)
                .build();

        return userRecord;
    }
    private static UserRecord initRecordB(KafkaProduceUserDto kafkaProduceTeamDto) {
        UserRecord userRecord = UserRecord.builder()
                .userId(kafkaProduceTeamDto.getUserId())
                .nickname(kafkaProduceTeamDto.getNickname())
                .sports("농구")
                .win(0L)
                .draw(0L)
                .lose(0L)
                .rating(1000L)
                .tier(Tier.초보자)
                .build();
        return userRecord;
    }

    //TODO 승리시 1승 up, rating 100 상승, point-service에는 point 경기인원수 x 100
    @Transactional
    @Override
    public void winGame(KafkaProduceRecordDto kafkaProduceRecordDto) {
        for (Long userId : kafkaProduceRecordDto.getUserIds()) {
            UserRecord userRecord = userRecordRepository.findByUserIdAndSports(userId, kafkaProduceRecordDto.getSports());
            userRecord.win();
            userRecordRepository.save(userRecord);
            MatchingRecord matchingRecord = MatchingRecord.of(kafkaProduceRecordDto);
            teamRecordRepository.save(matchingRecord);
            matchingRecord.addUserRecord(userRecord);
        }

        teamRecordService.updateTeamRecordByWinTeam(kafkaProduceRecordDto);
//        UserRecord userRecord = userRecordRepository.findByUserIdAndSports(userId, sports.name());
//        userRecord.win();
//        userRecordRepository.save(userRecord);
//
//        TeamRecord teamRecord = TeamRecord.of(matchingRoomId, winTeamId, winTeamName, loseTeamId, loseTeamName, result, winScore, loseScore, area);
//        teamRecordRepository.save(teamRecord);
//        teamRecord.addUserRecord(userRecord);
    }

    @Transactional
    @Override
    public void loseGame(KafkaProduceRecordDto kafkaProduceRecordDto) {
        for (Long userId : kafkaProduceRecordDto.getUserIds()) {
            UserRecord userRecord = userRecordRepository.findByUserIdAndSports(userId, kafkaProduceRecordDto.getSports());
            userRecord.lose();
            userRecordRepository.save(userRecord);
            MatchingRecord matchingRecord = MatchingRecord.of(kafkaProduceRecordDto);
            teamRecordRepository.save(matchingRecord);
            matchingRecord.addUserRecord(userRecord);
        }
        teamRecordService.updateTeamRecordByLoseTeam(kafkaProduceRecordDto);
//        UserRecord userRecord = userRecordRepository.findByUserIdAndSports(userId, sports.name());
//        userRecord.lose();
//        userRecordRepository.save(userRecord);

    }

    @Transactional
    @Override
    public void drawGame(KafkaProduceDrawRecordDto kafkaProduceDrawRecordDto) {
        for (Long userId : kafkaProduceDrawRecordDto.getUserIds()) {
            UserRecord userRecord = userRecordRepository.findByUserIdAndSports(userId, kafkaProduceDrawRecordDto.getSports());
            userRecord.draw();
            userRecordRepository.save(userRecord);
            MatchingRecord matchingRecord = MatchingRecord.of(kafkaProduceDrawRecordDto);
            teamRecordRepository.save(matchingRecord);
            matchingRecord.addUserRecord(userRecord);
        }
        teamRecordService.updateTeamRecordByDrawTeam(kafkaProduceDrawRecordDto);
    }

    @Override
    public List<UserRecordDto> getAllRecordByUserId(Long userId) {
        List<UserRecord> userRecord = userRecordRepository.findByUserId(userId);
        return userRecord
                .stream()
                .map(UserRecordDto::of)
                .toList();
    }

    @Override
    public Page<UserRecordDetailHistoryDto> getPointDetailHistory(String userId, Pageable pageable) {
        Page<MatchingRecord> matchingRecordList = userRecordRepositoryCustom.getUserRecordHistoryByUserId(Long.parseLong(userId), pageable);
        System.out.println("matchingRecordList = " + matchingRecordList);
        return PageableExecutionUtils.getPage(matchingRecordList.stream()
                .map(UserRecordDetailHistoryDto::of)
                .collect(Collectors.toList()), pageable, matchingRecordList::getTotalElements);
    }

    @Transactional
    @Override
    public void updateUserRecord(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto) {
        userRecordRepository.findByUserId(kafkaProduceUpdateUserProfileDto.getUserId())
                .forEach(userRecord -> {
                    userRecord.updateUserProfile(kafkaProduceUpdateUserProfileDto.getNickname());
                });

    }

    @Override
    public UserRecordDto getRecordByUserIdAndTeamId(Long userId, String sports) {
        return null;
    }

//    @Override
//    public UserRankingByRatingDto getUserRecordRankByRating(Long userId) {
//        UserRecord userRecord = userRecordRepository.findByUserIdAndSports(userId, "축구");
//        List<UserRecord> userRecordList = userRecordRepository.findBySportsOrderByRatingDesc("축구");
//        int rank = userRecordList.indexOf(userRecord) + 1;
//        return UserRankingByRatingDto.builder()
//                .userId(userRecord.getUserId())
//                .nickname(userRecord.getNickname())
//                .sports(userRecord.getSports())
//                .rating(userRecord.getRating())
//                .rank(rank)
//                .build();
//
//
//    }


}
