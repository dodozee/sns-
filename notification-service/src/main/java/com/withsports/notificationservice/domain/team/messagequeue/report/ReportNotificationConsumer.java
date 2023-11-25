package com.withsports.notificationservice.domain.team.messagequeue.report;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceReportDto;
import com.withsports.notificationservice.domain.team.dto.Producer.KafkaProduceTeamDto;
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
public class ReportNotificationConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService notificationService;

    @Transactional
    @KafkaListener(topics = "userReported")
    public void userReported(String kafkaMessage) throws Exception {
        log.debug("## NotificationConsumer.userReported");
        log.debug("#### kafka Message = {}", kafkaMessage);

        KafkaProduceReportDto kafkaProduceReportDto = objectMapper.readValue(kafkaMessage, KafkaProduceReportDto.class);

        notificationService.reportedUser(kafkaProduceReportDto);
    }
}
