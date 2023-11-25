package com.withsport.userservice.domain.admin.web.response;

import com.withsport.userservice.domain.admin.dto.ReportHistory;
import com.withsport.userservice.domain.admin.dto.ReportHistoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportHistoryResponse {
    private List<ReportHistory> reports;
    private Page page;
    public ReportHistoryResponse(List<ReportHistory> reports, int startPage, int totalPage) {
        this.reports = reports;
        this.page = new Page(startPage, totalPage);


    }
    @Data @AllArgsConstructor
    static class Page {
        int startPage;
        int totalPage;
    }
}
