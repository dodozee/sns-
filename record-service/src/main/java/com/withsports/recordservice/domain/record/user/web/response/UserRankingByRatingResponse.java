package com.withsports.recordservice.domain.record.user.web.response;


import com.withsports.recordservice.domain.record.user.entity.Tier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRankingByRatingResponse {
    private Long userId;
    private String nickname;
    private Long rating;
    private Long ranking;
    private Tier tier;
}
