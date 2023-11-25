package com.withsport.userservice.domain.admin.web;


import com.withsport.userservice.domain.admin.dto.ReportHistory;
import com.withsport.userservice.domain.admin.dto.ReportHistoryDto;
import com.withsport.userservice.domain.admin.service.ReportService;
import com.withsport.userservice.domain.admin.web.request.ReportUserRequest;
import com.withsport.userservice.domain.admin.web.response.ReportHistoryResponse;
import com.withsport.userservice.global.dto.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminUserController {

    private final ReportService reportService;


    //TODO 회원 신고
    @PostMapping("/user/report")
    ResponseEntity<Result> reportUser(@Valid @RequestHeader(value="user-id") String userId,
                                      @RequestBody ReportUserRequest reportUserRequest) throws Exception {
        Long reportedUserId = reportUserRequest.getReportedUserId();
        String content = reportUserRequest.getReason();
        reportService.reportUser(Long.valueOf(userId), reportedUserId, content);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("회원 신고가 완료되었습니다."));
    }

    //TODO (관리자) 회원 신고 목록 전체 조회
    @GetMapping("/admin/user/reports")
    ResponseEntity<Result> getReportedUser(@Valid @RequestHeader(value="user-id") String userId,
                                           @PageableDefault Pageable pageable){

        Page<ReportHistoryDto> reportHistorys = reportService.getReportHistory(pageable);

        List<ReportHistory> orderList = reportHistorys.stream()
                .map(ReportHistory::of)
                .toList();

        ReportHistoryResponse reportHistoryResponse = new ReportHistoryResponse(
                orderList,
                reportHistorys.getNumber(),
                reportHistorys.getTotalPages()
        );


        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(reportHistoryResponse, "회원 신고 목록 조회가 완료되었습니다."));
    }




}
