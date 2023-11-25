package com.withsport.userservice.domain.user.dto.producer;


import com.withsport.userservice.domain.user.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaProduceReportDto {
    private Long reporterUserId;
    private String reporterUserNickname;
    private Long reportedUserId;
    private String reportedUserNickname;
    private String reason;

    public static KafkaProduceReportDto of(Report report){
        return KafkaProduceReportDto.builder()
                .reporterUserId(report.getReporterUserId())
                .reporterUserNickname(report.getReporterUserNickname())
                .reportedUserId(report.getReportedUserId())
                .reportedUserNickname(report.getReportedUserNickname())
                .reason(report.getReason())
                .build();
    }

}
