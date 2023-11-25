package com.withsports.recordservice.domain.record.user.dto;


import com.withsports.recordservice.domain.record.user.entity.Tier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRankingByRatingDto {
    private Long userId;
    private String nickname;
    private Long rating;
    private Long ranking;
    private Tier tier;
}
