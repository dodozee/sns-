package com.withsports.matchingservice.domain.matchingroom.messagequeue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.matchingservice.domain.matching.dto.producer.KafkaProduceFindMatchingTeamDto;
import com.withsports.matchingservice.domain.matching.dto.producer.KafkaProduceSearchingDto;
import com.withsports.matchingservice.domain.matching.redis.Matching;
import com.withsports.matchingservice.domain.matching.service.MatchingService;
import com.withsports.matchingservice.domain.matchingroom.dto.produce.KafkaProduceBookingCompletedDto;
import com.withsports.matchingservice.domain.matchingroom.dto.produce.KafkaProduceUpdateUserProfileDto;
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
public class MatchingRoomConsumer {

    private final ObjectMapper objectMapper;
    private final MatchingService matchingService;


    @Transactional
    @KafkaListener(topics = "bookingCompleted")
    public void searchingMatchingRoom(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingRoomConsumer.bookingCompleted");
        log.debug("#### kafka Message = {}", kafkaMessage);


        KafkaProduceBookingCompletedDto kafkaProduceBookingCompletedDto = objectMapper.readValue(kafkaMessage, KafkaProduceBookingCompletedDto.class);
        System.out.println("여기까진 호출 직전 startMatchingSearching" + kafkaProduceBookingCompletedDto.getMatchingId());
        matchingService.bookingCompleted(kafkaProduceBookingCompletedDto);
//        System.out.println("여기까진 호출 성공 startMatchingSearching" + kafkaProduceSearchingDto.getMatchingRoomId() + kafkaProduceSearchingDto.getRoomLeaderId() );
    }

    @Transactional
    @KafkaListener(topics = "updatedUserForMatching")
    public void updatedUserForMatching(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingRoomConsumer.updateUserPoint");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto = objectMapper.readValue(kafkaMessage, KafkaProduceUpdateUserProfileDto.class);

        matchingService.updatedUserForMatching(kafkaProduceUpdateUserProfileDto);
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







}
