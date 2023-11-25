package com.withsports.matchingservice.domain.matching.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.matchingservice.domain.matching.dto.producer.KafkaProduceFindMatchingTeamDto;
import com.withsports.matchingservice.domain.matching.dto.producer.KafkaProduceSearchingDto;
import com.withsports.matchingservice.domain.matching.redis.Matching;
import com.withsports.matchingservice.domain.matching.service.MatchingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class MatchingSearchingConsumer {

    private final ObjectMapper objectMapper;
    private final MatchingService matchingService;


    @Transactional
    @KafkaListener(topics = "startMatchingSearching")
    public void searchingMatchingRoom(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingSearchingConsumer.startMatchingSearching");
        log.debug("#### kafka Message = {}", kafkaMessage);


        KafkaProduceSearchingDto kafkaProduceSearchingDto = objectMapper.readValue(kafkaMessage, KafkaProduceSearchingDto.class);
        System.out.println("여기까진 호출 직전 startMatchingSearching" + kafkaProduceSearchingDto.getMatchingRoomId() + kafkaProduceSearchingDto.getRoomLeaderId() );
        matchingService.startMatching(kafkaProduceSearchingDto.getMatchingRoomId(),kafkaProduceSearchingDto.getRoomLeaderId());
//        System.out.println("여기까진 호출 성공 startMatchingSearching" + kafkaProduceSearchingDto.getMatchingRoomId() + kafkaProduceSearchingDto.getRoomLeaderId() );
    }

//    @Transactional
//    @KafkaListener(topics = "putMatchingPool")
//    public void putMatchingPool(String kafkaMessage) throws Exception {
//        log.debug("## MatchingSearchingConsumer.putMatchingPool");
//        log.debug("#### kafka Message = {}", kafkaMessage);
//
//        KafkaProducePutMatchingPoolDto kafkaProducePutMatchingPoolDto = objectMapper.readValue(kafkaMessage, KafkaProducePutMatchingPoolDto.class);
//
//        //MatchingDto로 다시 변환
//        MatchingDto matchingDto = new MatchingDto(kafkaProducePutMatchingPoolDto.getMatchingRoomId(), kafkaProducePutMatchingPoolDto.getSports(), kafkaProducePutMatchingPoolDto.getSumRating(), kafkaProducePutMatchingPoolDto.getCapacity());
//
//        matchingService.matchingPoolScheduler(matchingDto);
//    }

    @Transactional
    @KafkaListener(topics = "findMatchingTeam")
    public void findMatchingTeam(String kafkaMessage) throws Exception{
        log.debug("## MatchingSearchingConsumer.findMatchingTeam");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceFindMatchingTeamDto kafkaProduceFindMatchingTeamDto = objectMapper.readValue(kafkaMessage, KafkaProduceFindMatchingTeamDto.class);

        System.out.println("===============브로커로 findMatchingTeam 토픽 받음 ================");
        //MatchingDto로 다시 변환
        List<Matching> matchings = kafkaProduceFindMatchingTeamDto.getMatchings();
        matchingService.notifyMatchingUsers(matchings);
        matchingService.changeMatchingSuccessStatus(matchings);
        matchingService.saveMatchingRooms(matchings);
    }






}
