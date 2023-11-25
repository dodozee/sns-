package com.withsport.userservice.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportHistory {
    private Long id; // 신고 내역 id
    private Long reporterId; // 신고한 유저 id
    private String reporterNickname; // 신고한 유저의 닉네임
    private Long reportedId; // 신고당한 유저의 id
    private String reportedNickname; // 신고당한 유저의 닉네임
    private String reason; // 신고 사유
    private LocalDateTime createdAt; // 신고한 시간

    public static ReportHistory of(ReportHistoryDto reportHistoryDto) {
        return ReportHistory.builder()
                .id(reportHistoryDto.getId())
                .reporterId(reportHistoryDto.getReporterId())
                .reporterNickname(reportHistoryDto.getReporterNickname())
                .reportedId(reportHistoryDto.getReportedId())
                .reportedNickname(reportHistoryDto.getReportedNickname())
                .reason(reportHistoryDto.getReason())
                .createdAt(reportHistoryDto.getCreatedAt())
                .build();
    }
}
