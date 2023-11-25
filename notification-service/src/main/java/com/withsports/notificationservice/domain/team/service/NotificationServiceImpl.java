package com.withsports.notificationservice.domain.team.service;

import com.withsports.notificationservice.domain.team.dto.FindNotificationDto;
import com.withsports.notificationservice.domain.team.dto.Producer.*;
import com.withsports.notificationservice.domain.team.dto.UpdateNotificationDto;
import com.withsports.notificationservice.domain.team.entity.Notification;
import com.withsports.notificationservice.domain.team.exception.NotExistNotification;
import com.withsports.notificationservice.domain.team.repository.NotificationRepository;
import com.withsports.notificationservice.global.client.team.GetTeamResponse;
import com.withsports.notificationservice.global.client.team.TeamClient;
import com.withsports.notificationservice.global.dto.Yn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final TeamClient teamClient;

    @Override
    public List<FindNotificationDto> findNotificationByUserId(Long id) {
        Sort.Order readYnAsc = Sort.Order.asc("readYn");
        Sort.Order createdAtDesc = Sort.Order.desc("createdAt");

        Sort sort = Sort.by(List.of(readYnAsc, createdAtDesc));
        return notificationRepository.findByUserId(id, sort)
                .stream()
                .map(FindNotificationDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateNotification(UpdateNotificationDto dto) {
        Notification notification = notificationRepository.findById(dto.getId())
                .orElseThrow(() -> new NotExistNotification(dto.getId() + "는 없는 알림 고유번호입니다."));

        Yn readYn = dto.isRead() ? Yn.Y : Yn.N;
        notification.updateReadYn(readYn);
    }

    @Override
    public Long findNotificationCounts(Long userId, Yn readYn) {
        return notificationRepository.countByUserIdAndReadYn(userId, readYn);
    }

    @Transactional
    @Override
    public void acceptedJoinTeam(KafkaProduceTeamUserDto userDto) {

        String title = "팀 가입 완료";
        String teamName = userDto.getTeamName();
        String message = "[" + teamName + "] 팀에 가입되었습니다.";
        Notification notification = Notification.of(userDto.getUserId(), message, title);
        notificationRepository.save(notification);

    }


    @Transactional
    @Override
    public void placedJoinTeam(KafkaProduceTeamUserDto userDto) {
        GetTeamResponse teamResponse = teamClient.getTeam(String.valueOf(userDto.getTeamId())).getData();

        String title = "팀 가입 신청";
        String teamName = teamResponse.getTeamName();
        String message = "[" + teamName + "] 팀에 가입 신청이 왔습니다.";
        Notification notification = Notification.of(teamResponse.getLeaderId(), message, title);
        notificationRepository.save(notification);
    }

    @Transactional
    @Override
    public void deleteMatchingRoom(KafkaProduceRoomUserListDto kafkaProduceRoomUserListDto) {
        List<Long> userIds = kafkaProduceRoomUserListDto.getUserIdList();
        String title = "매칭방 삭제";
        String message = "[" + kafkaProduceRoomUserListDto.getRoomTitle() + "] 매칭방이 삭제되었습니다.";
        for (Long userId : userIds) {
            Notification notification = Notification.of(userId, message, title);
            notificationRepository.save(notification);
        }
    }

    @Transactional
    @Override
    public void fullMatchingRoom(KafkaProduceRoomFullDto kafkaProduceRoomFullDto) {
        String title = "매칭방 가득 참";
        String message = "[" + kafkaProduceRoomFullDto.getTitle() + "] 매칭방이 가득 찼습니다.";
        Notification notification = Notification.of(kafkaProduceRoomFullDto.getRoomLeaderId(), message, title);
        notificationRepository.save(notification);
    }

    @Transactional
    @Override
    public void successMatching(KafkaProduceMatchingDto kafkaProduceRoomUserListDto) {
        List<Long> userIds = kafkaProduceRoomUserListDto.getUserIds();
        String title = "매칭 성공";
        String message = "[" + kafkaProduceRoomUserListDto.getMyTeamName() + "] 팀과 [" + kafkaProduceRoomUserListDto.getYourTeamName() + "] 팀의 매칭이 성공했습니다. 각 팀장은 구장 예약을 해주세요.";
        for (Long userId : userIds) {
            Notification notification = Notification.of(userId, message, title);
            notificationRepository.save(notification);
        }
    }

    @Transactional
    @Override
    public void cancelMatching(KafkaProduceCancelMatchingDto kafkaProduceCancelMatchingDto) {
        List<Long> userIds = kafkaProduceCancelMatchingDto.getUserIds();
        String roomTitle = kafkaProduceCancelMatchingDto.getTitle();
        String title = "매칭 탐색 취소";
        String message = "[ " + roomTitle + "] 방 매칭 탐색이 취소되었습니다.";
        for (Long userId : userIds) {
            Notification notification = Notification.of(userId, message, title);
            notificationRepository.save(notification);
        }
    }

    @Transactional
    @Override
    public void endGameDraw(KafkaProduceEndGameDrawDto kafkaProduceEndGameDrawDto) {
        List<Long> userIds = kafkaProduceEndGameDrawDto.getUserIds();
        String title = "경기 종료";
        String message = "[" + kafkaProduceEndGameDrawDto.getDrawTeamName1() + "] 팀이 [" + kafkaProduceEndGameDrawDto.getDrawTeamName2() + "] 팀과 무승부 하였습니다.";
        for (Long userId : userIds) {
            Notification notification = Notification.of(userId, message, title);
            notificationRepository.save(notification);
        }

    }


    //TODO 결국 관리자에게 신고접수가 모여야함
    @Transactional
    @Override
    public void reportedUser(KafkaProduceReportDto kafkaProduceReportDto) {
        String title = "신고 접수";
        String message = "["+ kafkaProduceReportDto.getReporterUserNickname()+ "님으로 인해" +"[" + kafkaProduceReportDto.getReportedUserNickname() + "] 님이 신고되었습니다." + " 사유는 : [" +
                kafkaProduceReportDto.getReason()+" ] 입니다.";
        Notification notification = Notification.of(kafkaProduceReportDto.getReportedUserId(), message, title);
        notificationRepository.save(notification);
    }
    @Transactional
    @Override
    public void rejectedJoinTeam(KafkaProduceTeamUserDto kafkaProduceTeamUserDto) {
        String title = "팀 가입 거절";
        String message = "[" + kafkaProduceTeamUserDto.getTeamName() + "] 팀에 가입 거절되었습니다.";
        Notification notification = Notification.of(kafkaProduceTeamUserDto.getUserId(), message, title);
        notificationRepository.save(notification);
    }

    @Transactional
    @Override
    public void notifyByOrderAccepted(KafkaProduceNotificationOrderAcceptedDto kafkaProduceNotificationOrderAcceptedDto) {
        Long toUserId = kafkaProduceNotificationOrderAcceptedDto.getToUserId();
        Long fromUserId = kafkaProduceNotificationOrderAcceptedDto.getFromUserId();
        String gifticonName = kafkaProduceNotificationOrderAcceptedDto.getGifticonName();
        String toUserNickname = kafkaProduceNotificationOrderAcceptedDto.getToUserNickname();
        String fromUserNickname = kafkaProduceNotificationOrderAcceptedDto.getFromUserNickname();

        String title = "선물 완료";
        String message = "[ " +fromUserNickname+ " ]님이 [ "+ toUserNickname +" ]님에게 보내 주신"+ " [" + gifticonName + "] 기프티콘 선물이 완료되었습니다.";
        Notification notification = Notification.of(fromUserId, message, title);
        notificationRepository.save(notification);

        String title2 = "선물 받음";
        String message2 = "[ " +toUserNickname+ " ]님이 [ "+ fromUserNickname +" ]님에게 받은"+ " [" + gifticonName + "] 기프티콘 선물을 받았습니다.";
        Notification notification2 = Notification.of(toUserId, message2, title2);
        notificationRepository.save(notification2);


    }

    @Transactional
    @Override
    public void notifyOrderRejected(KafkaProduceNotificationOrderRejectedDto kafkaProduceNotificationOrderRejectedDto) {
        Long toUserId = kafkaProduceNotificationOrderRejectedDto.getToUserId();
        Long fromUserId = kafkaProduceNotificationOrderRejectedDto.getFromUserId();
        String gifticonName = kafkaProduceNotificationOrderRejectedDto.getGifticonName();
        String toUserNickname = kafkaProduceNotificationOrderRejectedDto.getToUserNickname();
        String fromUserNickname = kafkaProduceNotificationOrderRejectedDto.getFromUserNickname();

        String title = "주문 거절";
        String message = "[ " +fromUserNickname+ " ]님이 [ "+ toUserNickname +" ]님에게 보내 줄"+ " [" + gifticonName + "] 기프티콘 주문이 거절되었습니다." +  "사유- 포인트 부족, 현재 포인트 : [" + kafkaProduceNotificationOrderRejectedDto.getHadPoint() + "]";
        Notification notification = Notification.of(fromUserId, message, title);
        notificationRepository.save(notification);

    }

    @Transactional
    @Override
    public void alertBookingCompleted(KafkaProduceAlertBookingCompleted kafkaProduceAlertBookingCompleted) {
        List<Long> matchingRoomUserIds1 = kafkaProduceAlertBookingCompleted.getMatchingRoomUserIds1();
        List<Long> matchingRoomUserIds2 = kafkaProduceAlertBookingCompleted.getMatchingRoomUserIds2();
        String matchingRoomName1 = kafkaProduceAlertBookingCompleted.getMatchingRoomName1();
        String matchingRoomName2 = kafkaProduceAlertBookingCompleted.getMatchingRoomName2();
        String description = kafkaProduceAlertBookingCompleted.getDescription();

        String title = "경기장 예약 완료";
        String message = "[" + matchingRoomName1 + "] 매칭방과 [" + matchingRoomName2 + "] 매칭방의 예약이 완료되었습니다. " + description;
        for (Long userId : matchingRoomUserIds1) {
            Notification notification = Notification.of(userId, message, title);
            notificationRepository.save(notification);
        }
        for (Long userId : matchingRoomUserIds2) {
            Notification notification = Notification.of(userId, message, title);
            notificationRepository.save(notification);
        }

    }


    @Transactional
    @Override
    public void startGame(KafkaProduceStartGameDto kafkaProduceStartGameDto) {
        List<Long> userIds = kafkaProduceStartGameDto.getUserIds();
        String title = "경기 시작";
        String message = "경기가 시작되었습니다.";
        for (Long userId : userIds) {
            Notification notification = Notification.of(userId, message, title);
            notificationRepository.save(notification);
        }
    }
    @Transactional
    @Override
    public void endGame(KafkaProduceEndGameDto kafkaProduceEndGameDto) {
        List<Long> userIds = kafkaProduceEndGameDto.getUserIds();
        String title = "경기 종료";
        String message = "[" + kafkaProduceEndGameDto.getWinTeamName() + "] 팀이 [" + kafkaProduceEndGameDto.getLoseTeamName() + "] 팀을 이겼습니다.";
        for (Long userId : userIds) {
            Notification notification = Notification.of(userId, message, title);
            notificationRepository.save(notification);
        }
    }



    @Transactional
    @Override
    public void deleteTeam(KafkaProduceTeamDto userDto) {

        List<Long> userIds = userDto.getUserIds();
        String teamName = userDto.getTeamName();

        for (Long id : userIds) {
            alertDeleteToOne(id, teamName);
        }

    }


    private void alertDeleteToOne(Long userId, String teamName) {
        String title = "팀 삭제";
        String message = "[" + teamName + "] 팀이 삭제되었습니다.";
        Notification notification = Notification.of(userId, message, title);
        notificationRepository.save(notification);
    }

//    private void alertJoinToOne(Long userId, GetTeamResponse teamResponse) {
//        String title = "팀 가입 완료";
//        String teamName = teamResponse.getTeamName();
//        String message = "[" + teamName + "] 팀에 가입되었습니다.";
//        Notification notification = Notification.of(userId, message, title);
//        notificationRepository.save(notification);
//    }


}
