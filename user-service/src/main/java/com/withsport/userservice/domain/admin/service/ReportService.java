package com.withsport.userservice.domain.admin.service;

import com.withsport.userservice.domain.admin.dto.ReportHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface ReportService {
    void reportUser(Long reporterId, Long reportedId, String reason) throws Exception;

    Page<ReportHistoryDto> getReportHistory(Pageable pageable);

}
