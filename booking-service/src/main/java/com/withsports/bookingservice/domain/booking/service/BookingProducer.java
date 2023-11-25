package com.withsports.bookingservice.domain.booking.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.withsports.bookingservice.domain.booking.dto.producer.KafkaProduceBookingCompletedDto;
import com.withsports.bookingservice.domain.booking.entity.BookingMatchingRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookingProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

//    public void orderPlaced(Order order) throws Exception{
//        objectMapper.registerModule(new JavaTimeModule());
//        KafkaProduceOrderPlacedDto kafkaProduceOrderDto = KafkaProduceOrderPlacedDto.of(order);
//        String json = objectMapper.writeValueAsString(kafkaProduceOrderDto);
//        kafkaTemplate.send("orderPlaced", json);
//        log.info("[OrderProducer] orderPlaced = {}", json);
//
//    }



    public void bookingCompleted(BookingMatchingRoom bookingMatchingRoom,String description) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        KafkaProduceBookingCompletedDto kafkaProduceBookingCompletedDto = KafkaProduceBookingCompletedDto.of(bookingMatchingRoom,description);
        String json = objectMapper.writeValueAsString(kafkaProduceBookingCompletedDto);
        kafkaTemplate.send("bookingCompleted", json);
        log.info("[BookingProducer] bookingCompleted = {}", json);
    }

//    public void orderAccepted(Order order) throws Exception{
//        objectMapper.registerModule(new JavaTimeModule());
//        KafkaProduceOrderDto kafkaProduceOrderDto = KafkaProduceOrderDto.of(order);
//        String json = objectMapper.writeValueAsString(kafkaProduceOrderDto);
//        kafkaTemplate.send("orderAccepted", json);
//        log.info("[OrderProducer] orderAccepted = {}", json);
//
//    }

//    public void orderRejected(Order order) throws Exception{
//        objectMapper.registerModule(new JavaTimeModule());
//        KafkaProduceOrderDto kafkaProduceOrderDto = KafkaProduceOrderDto.of(order);
//        String json = objectMapper.writeValueAsString(kafkaProduceOrderDto);
//        kafkaTemplate.send("orderRejected", json);
//        log.info("[OrderProducer] orderRejected = {}", json);
//
//    }
}
