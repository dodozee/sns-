package com.withsport.userservice.domain.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsport.userservice.domain.user.dto.producer.KafkaProduceReportDto;
import com.withsport.userservice.domain.user.entity.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;


    // 회원정보 추가로 입력하면 발생
    public void reportedUser(Report report) throws Exception{
        KafkaProduceReportDto kafkaProduceReportDto = KafkaProduceReportDto.of(report);
        String json = objectMapper.writeValueAsString(kafkaProduceReportDto);
        kafkaTemplate.send("userReported", json);
        log.info("[ReportProducer] userReported = {}", json);
    }
}
