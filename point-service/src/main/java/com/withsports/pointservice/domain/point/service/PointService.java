package com.withsports.pointservice.domain.point.service;

import com.withsports.pointservice.domain.point.dto.PointDetailHistoryDto;
import com.withsports.pointservice.domain.point.dto.producer.*;
import com.withsports.pointservice.domain.point.dto.PointDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointService {
    void initUserPoint(KafkaProduceInitUserDto kafkaProduceInitUserDto);

    void increasePointByGameWin(KafkaProduceGameWinPointDto kafkaProduceGameWinPointDto);

    void increasePointByGameLose(KafkaProduceGameLosePointDto kafkaProduceGameLosePointDto);

    void increasePointByGameDraw(KafkaProduceGameDrawPointDto kafkaProduceGameDrawPointDto);

    void orderPlaced(KafkaProduceOrderPlacedDto kafkaProduceOrderPlacedDto) throws Exception;

    PointDto getPoint(String userId);

    Page<PointDetailHistoryDto> getPointDetailHistory(String userId, Pageable pageable
    );

    void updateUserPoint(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto);
}
