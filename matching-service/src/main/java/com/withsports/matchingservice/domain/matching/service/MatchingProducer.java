package com.withsports.matchingservice.domain.matching.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.matchingservice.domain.matching.dto.producer.*;
import com.withsports.matchingservice.domain.matching.redis.Matching;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class MatchingProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    public void successMatching(List<Long> userIds, String myTeamName, String yourTeamName ) throws Exception{
        KafkaProduceMatchingDto kafkaSendTeamDto = KafkaProduceMatchingDto.builder()
                .myTeamName(myTeamName)
                .yourTeamName(yourTeamName)
                .userIds(userIds)
                .build();
        String json = objectMapper.writeValueAsString(kafkaSendTeamDto);
        kafkaTemplate.send("matchingSucceeded", json);
        log.info("[MatchingProducer] matchingSucceeded = {}", json);
    }

    public void startMatchingSearching(Long matchingRoomId, Long roomLeaderId) throws Exception{
        KafkaProduceSearchingDto kafkaProduceSearchingDto = KafkaProduceSearchingDto.builder()
                .matchingRoomId(matchingRoomId)
                .roomLeaderId(roomLeaderId)
                .build();
        String json = objectMapper.writeValueAsString(kafkaProduceSearchingDto);
        kafkaTemplate.send("startMatchingSearching", json);
        log.info("[MatchingProducer] startMatchingSearching = {}", json);
    }

    public void alertStartGame(List<Long> userIds) throws Exception{
        KafkaProduceStartGameDto kafkaProduceStartGameDto = KafkaProduceStartGameDto.builder()
                .userIds(userIds)
                .build();
        String json = objectMapper.writeValueAsString(kafkaProduceStartGameDto);
        kafkaTemplate.send("gameStarted", json);
        log.info("[MatchingProducer] gameStarted = {}", json);
    }

    public void alertEndGame(List<Long> userIds, String winTeamName, String loseTeamName) throws Exception{
        KafkaProduceEndGameDto kafkaProduceEndGameDto = KafkaProduceEndGameDto.builder()
                .userIds(userIds)
                .winTeamName(winTeamName)
                .loseTeamName(loseTeamName)
                .build();
        String json = objectMapper.writeValueAsString(kafkaProduceEndGameDto);
        kafkaTemplate.send("gameEnded", json);
        log.info("[MatchingProducer] gameEnded = {}", json);
    }

    public void alertEndGameDraw(List<Long> userIds, String drawTeamName1, String drawTeamName2) throws Exception{
        KafkaProduceEndGameDrawDto kafkaProduceEndGameDrawDto = KafkaProduceEndGameDrawDto.builder()
                .userIds(userIds)
                .drawTeamName1(drawTeamName1)
                .drawTeamName2(drawTeamName2)
                .build();
        String json = objectMapper.writeValueAsString(kafkaProduceEndGameDrawDto);
        kafkaTemplate.send("gameEndedDraw", json);
        log.info("[MatchingProducer] gameEndedDraw = {}", json);
    }

    public void winGame(List<Long> userIds, String sports, Long matchingRoomId, Long winTeamId, String winTeamName, Long loseTeamId, String loseTeamName, String result, Long winScore, Long loseScore, String area) throws Exception{
        KafkaProduceRecordDto kafkaProduceRecordDto = KafkaProduceRecordDto.builder()
                .userIds(userIds)
                .sports(sports)
                .matchingRoomId(matchingRoomId)
                .winTeamId(winTeamId)
                .winTeamName(winTeamName)
                .loseTeamId(loseTeamId)
                .loseTeamName(loseTeamName)
                .result(result)
                .winScore(winScore)
                .loseScore(loseScore)
                .area(area)
                .build();
        KafkaProduceGameWinPointDto kafkaProduceGameWinPointDto = KafkaProduceGameWinPointDto.builder()
                .userIds(userIds)
                .matchingRoomId(matchingRoomId)
                .teamName(winTeamName)
                .opponentTeamName(loseTeamName)
                .build();
        String jsonRecord = objectMapper.writeValueAsString(kafkaProduceRecordDto);
        String jsonPoint = objectMapper.writeValueAsString(kafkaProduceGameWinPointDto);
        kafkaTemplate.send("gameWon", jsonRecord);
        kafkaTemplate.send("gameWinPointIncreased", jsonPoint);
        log.info("[MatchingProducer] winGame = {}", jsonRecord);
    }





    public void loseGame(List<Long> userIds, String sports, Long matchingRoomId, Long loseTeamId, String loseTeamName, Long winTeamId, String winTeamName, String result, Long winScore, Long loseScore, String area) throws Exception{
        KafkaProduceRecordDto kafkaProduceRecordDto = KafkaProduceRecordDto.builder()
                .userIds(userIds)
                .sports(sports)
                .matchingRoomId(matchingRoomId)
                .winTeamId(winTeamId)
                .winTeamName(winTeamName)
                .loseTeamId(loseTeamId)
                .loseTeamName(loseTeamName)
                .result(result)
                .winScore(winScore)
                .loseScore(loseScore)
                .area(area)
                .build();
        KafkaProduceGameLosePointDto kafkaProduceGameLosePointDto = KafkaProduceGameLosePointDto.builder()
                .userIds(userIds)
                .matchingRoomId(matchingRoomId)
                .teamName(loseTeamName)
                .opponentTeamName(winTeamName)
                .build();
        String jsonRecord = objectMapper.writeValueAsString(kafkaProduceRecordDto);
        String jsonPoint = objectMapper.writeValueAsString(kafkaProduceGameLosePointDto);
        kafkaTemplate.send("gameLost", jsonRecord);
        kafkaTemplate.send("gameLosePointIncreased", jsonPoint);

        log.info("[MatchingProducer] loseGame = {}", jsonRecord);
        log.info("[MatchingProducer] loseGame = {}", jsonPoint);

    }

    public void drawGame(List<Long> userIds, String sports, Long matchingRoomId, Long teamId, String teamName, Long opponentTeamId, String teamName1, String result, Long score, Long opponentScore, String area) throws JsonProcessingException {
        KafkaProduceDrawRecordDto kafkaProduceDrawRecordDto = KafkaProduceDrawRecordDto.builder()
                .userIds(userIds)
                .sports(sports)
                .matchingRoomId(matchingRoomId)
                .oneDrawTeamId(teamId)
                .oneDrawTeamName(teamName)
                .twoDrawTeamId(opponentTeamId)
                .twoDrawTeamName(teamName1)
                .result(result)
                .oneScore(score)
                .twoScore(opponentScore)
                .area(area)
                .build();
        KafkaProduceGameDrawPointDto kafkaProduceGameDrawPointDto = KafkaProduceGameDrawPointDto.builder()
                .userIds(userIds)
                .matchingRoomId(matchingRoomId)
                .teamName(teamName)
                .opponentTeamName(teamName1)
                .build();

        String jsonRecord = objectMapper.writeValueAsString(kafkaProduceDrawRecordDto);
        String jsonPoint = objectMapper.writeValueAsString(kafkaProduceGameDrawPointDto);
        kafkaTemplate.send("gameDraw", jsonRecord);
        kafkaTemplate.send("gameDrawPointIncreased", jsonPoint);
        log.info("[MatchingProducer] gameDraw = {}", jsonRecord);
        log.info("[MatchingProducer] gameDraw = {}", jsonPoint);


    }

    //TODO 매칭된 즉후 보내는 메세지 - List<Matching>을 보내면 받은 메서드에서는 매칭된 팀을 찾아서 알림을 보내줌
    public void findMatchingTeam(List<Matching> matchingList) throws JsonProcessingException {
        KafkaProduceFindMatchingTeamDto kafkaProduceFindMatchingTeamDto = KafkaProduceFindMatchingTeamDto.builder()
                .matchingList(matchingList)
                .build();

        String json = objectMapper.writeValueAsString(kafkaProduceFindMatchingTeamDto);
        System.out.println("===============브로커로 findMatchingTeam 토픽 보내기 직전 ================");
        kafkaTemplate.send("findMatchingTeam", json);
        System.out.println("===============브로커로 findMatchingTeam 토픽 보내기 직후 ================");
        System.out.println("===============브로커로 findMatchingTeamInitBooking 토픽 보내기 직전 ================");
        kafkaTemplate.send("findMatchingTeamInitBooking", json);
        System.out.println("===============브로커로 findMatchingTeamInitBooking 토픽 보내기 직후 ================");

        log.info("[MatchingProducer] findMatchingTeam = {}", json);

    }

    public void cancelMatching(List<Long> userIds, String title) throws JsonProcessingException {
        KafkaProduceCancelMatchingDto kafkaProduceCancelMatchingDto = KafkaProduceCancelMatchingDto.builder()
                .userIds(userIds)
                .title(title)
                .build();
        String json = objectMapper.writeValueAsString(kafkaProduceCancelMatchingDto);
        kafkaTemplate.send("matchingCanceled", json);
        log.info("[MatchingProducer] matchingCanceled = {}", json);

    }



    public void beforeBooking(com.withsports.matchingservice.domain.matching.entity.Matching matching) throws JsonProcessingException {
        KafkaProduceMatchingIdDto kafkaProduceMatchingIdDto = KafkaProduceMatchingIdDto.builder()
                .matchingId(matching.getId())
                .matchingRoomId1(matching.getMatchingRoomId1())
                .build();

        String json = objectMapper.writeValueAsString(kafkaProduceMatchingIdDto);
        kafkaTemplate.send("saveMatchingId", json);
        log.info("[MatchingProducer] beforeBooking = {}", json);

    }


    public void alertBookingCompleted(List<Long> allUserIdByMatchingRoomId1, List<Long> allUserIdByMatchingRoomId2, String matchingRoomName1, String matchingRoomName2, String description) throws JsonProcessingException {
        KafkaProduceAlertBookingCompleted kafkaProduceAlertBookingCompleted = KafkaProduceAlertBookingCompleted.builder()
                .matchingRoomUserIds1(allUserIdByMatchingRoomId1)
                .matchingRoomUserIds2(allUserIdByMatchingRoomId2)
                .matchingRoomName1(matchingRoomName1)
                .matchingRoomName2(matchingRoomName2)
                .description(description)
                .build();

        String json = objectMapper.writeValueAsString(kafkaProduceAlertBookingCompleted);
        kafkaTemplate.send("alertBookingCompleted", json);
        log.info("[MatchingProducer] alertBookingCompleted = {}", json);
    }
}

