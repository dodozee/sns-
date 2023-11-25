package com.withsports.recordservice.domain.record.user.service;

import com.withsports.recordservice.domain.record.user.dto.UserRankingByRatingDto;
import com.withsports.recordservice.domain.record.user.dto.UserRecordDetailHistoryDto;
import com.withsports.recordservice.domain.record.user.dto.UserRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceDrawRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceRecordDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceUpdateUserProfileDto;
import com.withsports.recordservice.domain.record.user.dto.producer.KafkaProduceUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRecordService {

    UserRecordDto getRecordByUserId(Long userId, String sports);

    void initUserRecord(KafkaProduceUserDto kafkaProduceTeamDto);

    void winGame(KafkaProduceRecordDto kafkaProduceRecordDto);

    void loseGame(KafkaProduceRecordDto kafkaProduceRecordDto);

    void drawGame(KafkaProduceDrawRecordDto kafkaProduceDrawRecordDto);

    List<UserRecordDto> getAllRecordByUserId(Long userId);

    Page<UserRecordDetailHistoryDto> getPointDetailHistory(String userId, Pageable pageable);

    void updateUserRecord(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto);

    UserRecordDto getRecordByUserIdAndTeamId(Long userId, String teamId);

//    UserRankingByRatingDto getUserRecordRankByRating(Long userId);
}
