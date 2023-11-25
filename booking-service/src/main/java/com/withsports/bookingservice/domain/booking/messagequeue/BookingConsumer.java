package com.withsports.bookingservice.domain.booking.messagequeue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.bookingservice.domain.booking.dto.producer.KafkaProduceFindMatchingTeamDto;
import com.withsports.bookingservice.domain.booking.dto.producer.KafkaProduceMatchingIdDto;
import com.withsports.bookingservice.domain.booking.dto.producer.Matching;
import com.withsports.bookingservice.domain.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingConsumer {

    private final ObjectMapper objectMapper;
    private final BookingService bookingService;


    @Transactional
    @KafkaListener(topics = "findMatchingTeamInitBooking")
    public void findMatchingTeam(String kafkaMessage) throws Exception {
        log.debug("## MatchingSearchingConsumer.findMatchingTeam");
        log.debug("#### kafka Message = {}", kafkaMessage);

        System.out.println("===================findMatchingTeamInitBooking====================s");
        KafkaProduceFindMatchingTeamDto kafkaProduceFindMatchingTeamDto = objectMapper.readValue(kafkaMessage, KafkaProduceFindMatchingTeamDto.class);

        //MatchingDto로 다시 변환
        List<Matching> matchings = kafkaProduceFindMatchingTeamDto.getMatchings();

        bookingService.initBooking(matchings);
    }

    @Transactional
    @KafkaListener(topics = "saveMatchingId")
    public void saveMatchingId(String kafkaMessage) throws Exception {
        log.debug("## MatchingSearchingConsumer.findMatchingTeam");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceMatchingIdDto kafkaProduceMatchingIdDto = objectMapper.readValue(kafkaMessage, KafkaProduceMatchingIdDto.class);

        //MatchingDto로 다시 변환
        Long matchingId = kafkaProduceMatchingIdDto.getMatchingId();
        Long matchingRoomId = kafkaProduceMatchingIdDto.getMatchingRoomId1();

        bookingService.saveMatchingId(matchingId,matchingRoomId);
    }
}
