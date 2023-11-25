package com.withsports.notificationservice.domain.team.messagequeue.booking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceAlertBookingCompleted;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceRoomFullDto;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceRoomUserListDto;
import com.withsports.notificationservice.domain.team.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class BookingNotificationConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;


    @Transactional
    @KafkaListener(topics = "alertBookingCompleted")
    public void alertBookingCompleted(String kafkaMessage) throws JsonProcessingException {
        log.debug("## MatchingRoomNotificationConsumer.matchingRoomDeleted");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceAlertBookingCompleted kafkaProduceAlertBookingCompleted = objectMapper.readValue(kafkaMessage, KafkaProduceAlertBookingCompleted.class);

        notificationService.alertBookingCompleted(kafkaProduceAlertBookingCompleted);
    }






}
