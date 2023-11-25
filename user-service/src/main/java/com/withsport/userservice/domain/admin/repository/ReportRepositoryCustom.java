package com.withsport.userservice.domain.admin.repository;

import com.withsport.userservice.domain.user.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportRepositoryCustom {

    Page<Report> getReportHistory(Pageable pageable);
}
