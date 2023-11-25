package com.withsports.recordservice.domain.record.user.web.response;

import com.withsports.recordservice.domain.record.user.dto.UserRecordDetailHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor
public class UserRecordDetailHistoryResponse {
    private List<UserRecordDetailHistory> records;
    private Page page;

    @Data
    @AllArgsConstructor
    static class Page {
        int startPage;
        int totalPage;
    }

    public UserRecordDetailHistoryResponse(List<UserRecordDetailHistory> records, int startPage, int totalPage) {
        this.records = records;
        this.page = new Page(startPage, totalPage);

    }
}
