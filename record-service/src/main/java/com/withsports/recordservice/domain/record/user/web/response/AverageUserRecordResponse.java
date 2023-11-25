package com.withsports.recordservice.domain.record.user.web.response;

import com.withsports.recordservice.domain.record.user.dto.UserRecordDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class AverageUserRecordResponse {
    private Long userId;
    private String nickname;
    private String sports;
    private Long win;
    private Long lose;
    private Long draw;
    private Long rating;
    private String tier;

    public AverageUserRecordResponse(List<UserRecordDto> userRecordDtos) {
        this.userId = userRecordDtos.get(0).getUserId();
        this.nickname = userRecordDtos.get(0).getNickname();
        Total loop = loop(userRecordDtos);
        this.win = loop.totalWins;
        this.lose = loop.totalLoses;
        this.draw = loop.totalDraws;
        this.rating = loop.highestRating;
        this.tier = getTierDescription(loop.highestRating);
    }

    private Total loop(List<UserRecordDto> userRecordDtos) {
        Long totalWins = 0L;
        Long totalLoses = 0L;
        Long totalDraws = 0L;
        Long highestRating = 0L;
        for (UserRecordDto record : userRecordDtos) {
            totalWins += record.getWin();
            totalLoses += record.getLose();
            totalDraws += record.getDraw();

            if (record.getRating() > highestRating) {
                highestRating = record.getRating();
            }

        }

        return new Total(totalWins, totalLoses, totalDraws, highestRating);

    }

    public String getTierDescription(Long rating) {
        if (rating >= 3000) {
            return "신";
        } else if (rating >= 2600) {
            return "프로";
        } else if (rating >= 2200) {
            return "준프로";
        } else if (rating >= 2000) {
            return "전국대표";
        } else if (rating >= 1600) {
            return "지역대표";
        } else if (rating >= 1400) {
            return "동네대표";
        } else if (rating >= 1200) {
            return "아마추어";
        } else if (rating >= 1000) {
            return "초보자";
        } else {
            return "왕초보자";
        }
    }


    @Data
    @NoArgsConstructor
    static class Total {
        private Long totalWins;
        private Long totalLoses;
        private Long totalDraws;
        private Double winRate;
        private Long highestRating;

        public Total(Long totalWins, Long totalLoses, Long totalDraws, Long highestRating) {
            this.totalWins = totalWins;
            this.totalLoses = totalLoses;
            this.totalDraws = totalDraws;
            this.winRate = (double) totalWins / (totalWins + totalLoses + totalDraws);
            this.highestRating = highestRating;
        }

    }
}
