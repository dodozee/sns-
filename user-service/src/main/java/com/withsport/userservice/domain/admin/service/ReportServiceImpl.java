package com.withsport.userservice.domain.admin.service;

import com.withsport.userservice.domain.admin.dto.ReportHistoryDto;
import com.withsport.userservice.domain.admin.repository.ReportRepositoryCustom;
import com.withsport.userservice.domain.user.entity.Report;
import com.withsport.userservice.domain.user.entity.User;
import com.withsport.userservice.domain.admin.repository.ReportRepository;
import com.withsport.userservice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ReportProducer reportProducer;
    private final ReportRepositoryCustom reportRepositoryCustom;

    @Transactional
    @Override
    public void reportUser(Long reporterUserId, Long reportedUserId, String reason) throws Exception {
        User reporterUser = userRepository.findById(reporterUserId).orElseThrow();
        User reportedUser = userRepository.findById(reportedUserId).orElseThrow();

        reportRepository.save(Report.of(
                reporterUser.getId(),
                reporterUser.getNickname(),
                reportedUser.getId(),
                reportedUser.getNickname(),
                reason));

        reportProducer.reportedUser(Report.of(
                reporterUser.getId(),
                reporterUser.getNickname(),
                reportedUser.getId(),
                reportedUser.getNickname(),
                reason));

    }

    @Override
    public Page<ReportHistoryDto> getReportHistory(Pageable pageable) {
        Page<Report> sentOrderHistory = reportRepositoryCustom.getReportHistory(pageable);
        return PageableExecutionUtils.getPage(sentOrderHistory.stream()
                .map(ReportHistoryDto::of)
                .collect(Collectors.toList()), pageable, sentOrderHistory::getTotalElements);
    }
}
