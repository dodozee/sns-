package com.withsport.userservice.domain.admin.web.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportUserRequest {
    private Long reportedUserId; // 신고당한 유저의 id
    private String reason; // 신고 사유
}
