package com.withsports.pointservice.domain.point.service;


import com.withsports.pointservice.domain.point.dto.PointDetailHistoryDto;
import com.withsports.pointservice.domain.point.dto.producer.*;
import com.withsports.pointservice.domain.point.entity.Point;
import com.withsports.pointservice.domain.point.entity.PointTransaction;
import com.withsports.pointservice.domain.point.exception.NotExistUserException;
import com.withsports.pointservice.domain.point.repository.PointRepository;
import com.withsports.pointservice.domain.point.dto.PointDto;
import com.withsports.pointservice.domain.point.repository.PointTransactionRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PointServiceImpl implements PointService{


    private final PointRepository pointRepository;
    private final PointTransactionRepositoryCustom pointTransactionRepositoryCustom;
    private final PointProducer pointProducer;

    @Transactional
    @Override
    public void initUserPoint(KafkaProduceInitUserDto kafkaProduceInitUserDto) {
        if(pointRepository.findByUserId(kafkaProduceInitUserDto.getUserId()) != null){
            return ;
        }
        Point point = Point.init(kafkaProduceInitUserDto.getUserId(), kafkaProduceInitUserDto.getNickname(), 1000L); //회원가입시 1000 point
        pointRepository.save(point);

    }

    @Transactional
    @Override
    public void increasePointByGameWin(KafkaProduceGameWinPointDto kafkaProduceGameWinPointDto) {
        List<Long> userIds = kafkaProduceGameWinPointDto.getUserIds();
        int size = userIds.size();
        String teamName = kafkaProduceGameWinPointDto.getTeamName();
        String opponentTeamName = kafkaProduceGameWinPointDto.getOpponentTeamName();
        Long matchingRoomId = kafkaProduceGameWinPointDto.getMatchingRoomId();
        for (Long userId : userIds) {
            Point point = pointRepository.findByUserId(userId);
            point.increasePoint(size*100L,"게임 승리 보상 : " + teamName + " vs " + opponentTeamName + " / 승리 /matching.No :"+ matchingRoomId);
        }
    }

    @Transactional
    @Override
    public void increasePointByGameLose(KafkaProduceGameLosePointDto kafkaProduceGameLosePointDto) {
        List<Long> userIds = kafkaProduceGameLosePointDto.getUserIds();
        int size = userIds.size();
        String teamName = kafkaProduceGameLosePointDto.getTeamName();
        String opponentTeamName = kafkaProduceGameLosePointDto.getOpponentTeamName();
        Long matchingRoomId = kafkaProduceGameLosePointDto.getMatchingRoomId();
        for (Long userId : userIds) {
            Point point = pointRepository.findByUserId(userId);
            point.increasePoint(size*50L,"게임 패배 보상 : " + teamName + " vs " + opponentTeamName + " / 패배 /matching.No :"+ matchingRoomId);
        }

    }

    @Transactional
    @Override
    public void increasePointByGameDraw(KafkaProduceGameDrawPointDto kafkaProduceGameDrawPointDto) {
        List<Long> userIds = kafkaProduceGameDrawPointDto.getUserIds();
        int size = userIds.size();
        String teamName = kafkaProduceGameDrawPointDto.getTeamName();
        String opponentTeamName = kafkaProduceGameDrawPointDto.getOpponentTeamName();
        Long matchingRoomId = kafkaProduceGameDrawPointDto.getMatchingRoomId();
        for (Long userId : userIds) {
            Point point = pointRepository.findByUserId(userId);
            point.increasePoint(size*75L,"게임 참여 보상 : " + teamName + " vs " + opponentTeamName + " / 무승부 /matching.No :"+ matchingRoomId);
        }

    }

    @Transactional
    @Override
    public void orderPlaced(KafkaProduceOrderPlacedDto kafkaProduceOrderPlacedDto) throws Exception {
        System.out.println("orderPlaced 시작 ============kafkaProduceOrderPlacedDto = " + kafkaProduceOrderPlacedDto);
        Long fromUserId = kafkaProduceOrderPlacedDto.getFromUserId();
        System.out.println("orderPlaced 시작 =====fromUserId = " + fromUserId);
        String fromUserName = kafkaProduceOrderPlacedDto.getFromUserNickname();
        System.out.println("orderPlaced 시작 =====fromUserName = " + fromUserName);
        String toUserName = kafkaProduceOrderPlacedDto.getToUserNickname();
        System.out.println("orderPlaced 시작 =====toUserName = " + toUserName);
        String gifticonName = kafkaProduceOrderPlacedDto.getGifticonName();
        System.out.println("orderPlaced 시작 =====gifticonName = " + gifticonName);
        Long gifticonId = kafkaProduceOrderPlacedDto.getGifticonId();
        System.out.println("orderPlaced 시작 =====gifticonId = " + gifticonId);
        Long gifticonPrice = kafkaProduceOrderPlacedDto.getPrice()* kafkaProduceOrderPlacedDto.getAmount();
        Point point = pointRepository.findByUserId(fromUserId);
        System.out.println("orderPlaced 시작 =====point.getNickname() = " + point.getNickname());
        System.out.println("orderPlaced 시작 =====point.getBalance() = " + point.getBalance());
        System.out.println("orderPlaced 시작 =====gifticonPrice = " + gifticonPrice);
        boolean checked = checkValidationDecreasePoint(point, kafkaProduceOrderPlacedDto);
        System.out.println("orderPlaced 시작 =====checked = " + checked);
        if(checked) {
            System.out.println("orderPlaced 시작 =====if문 시작? = " + checked);
            point.decreasePoint(gifticonPrice, "기프티콘 구매 : No." + gifticonId + " / " + fromUserName + "님이 " + gifticonName + " 기프티콘 을 " + toUserName + "님에게 / point: " + gifticonPrice + " 차감");
            pointProducer.sendOrderAccepted(kafkaProduceOrderPlacedDto, point.getId());
        }else{
            pointProducer.sendOrderRejectedByNotEnoughPoint(kafkaProduceOrderPlacedDto, point.getBalance());
        }


    }

    @Override
    public PointDto getPoint(String userId) {
        Point point = pointRepository.findByUserId(Long.parseLong(userId));
        if(point != null) {
            return PointDto.builder()
                    .userId(point.getUserId())
                    .nickname(point.getNickname())
                    .balance(point.getBalance())
                    .build();
        }else{
            throw new NotExistUserException("해당 유저가 존재하지 않습니다.");
        }
    }

    //TODO : QueryDSL로 바꿔야함
    @Override
    public Page<PointDetailHistoryDto> getPointDetailHistory(String userId, Pageable pageable) {
        Page<PointTransaction> pointTransactionList = pointTransactionRepositoryCustom.getPointTransactionHistoryByUserId(Long.parseLong(userId), pageable);
        System.out.println("pointTransactionList = " + pointTransactionList);
        return PageableExecutionUtils.getPage(pointTransactionList.stream()
                .map(PointDetailHistoryDto::of)
                .collect(Collectors.toList()), pageable, pointTransactionList::getTotalElements);
    }

    @Transactional
    @Override
    public void updateUserPoint(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto) {
        Point point = pointRepository.findByUserId(kafkaProduceUpdateUserProfileDto.getUserId());
        point.updateUserProfile(kafkaProduceUpdateUserProfileDto.getNickname());
    }

//    @Override
//    public void orderPlaced(KafkaProduceOrderPlacedDto kafkaProduceOrderPlacedDto) {
//        Point point = pointRepository.findByUserId(kafkaProduceOrderPlacedDto.getFromUserId());
//        checkValidationDecreasePoint(point, kafkaProduceOrderPlacedDto);
//        point.decreasePoint(kafkaProduceOrderPlacedDto.getPrice() * kafkaProduceOrderPlacedDto.getAmount(),"기프티콘 구매 : No." + kafkaProduceOrderPlacedDto.getGifticonId() + " / "+ kafkaProduceOrderPlacedDto.getFromUserNickname() +"님이 " + kafkaProduceOrderPlacedDto.getGifticonName() + " 기프티콘 을 " + kafkaProduceOrderPlacedDto.getToUserNickname() + "님에게 / point: " + kafkaProduceOrderPlacedDto.getPrice() + " 차감");
//    }

    private boolean checkValidationDecreasePoint(Point point, KafkaProduceOrderPlacedDto kafkaProduceOrderPlacedDto) {
        System.out.println("=========checkValidationDecreasePoint메서드 실행 ==========point.getBalance() = " + point.getBalance());
        return point.getBalance() - kafkaProduceOrderPlacedDto.getPrice() * kafkaProduceOrderPlacedDto.getAmount() >= 0;
    }

}
