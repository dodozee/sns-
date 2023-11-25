package com.withsport.userservice.domain.admin.dto;

import com.withsport.userservice.domain.user.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportHistoryDto {
        private Long id;
        private Long reporterId;
        private String reporterNickname;
        private Long reportedId;
        private String reportedNickname;
        private String reason;
        private LocalDateTime createdAt;

    public static ReportHistoryDto of(Report report) {
        return ReportHistoryDto.builder()
                .id(report.getId())
                .reporterId(report.getReporterUserId())
                .reporterNickname(report.getReporterUserNickname())
                .reportedId(report.getReportedUserId())
                .reportedNickname(report.getReportedUserNickname())
                .reason(report.getReason())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
