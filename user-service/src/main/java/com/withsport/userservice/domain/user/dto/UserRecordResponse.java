package com.withsport.userservice.domain.user.dto;


import com.withsport.userservice.global.client.record.AverageUserRecordResponse;
import com.withsport.userservice.global.dto.Result;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRecordResponse {
    private Long userId;
    private String nickname;
    private String sports;
    private Long win;
    private Long lose;
    private Long draw;
    private Long rating;
    private String tier;

    public UserRecordResponse(Result<AverageUserRecordResponse> record) {
        this.userId = record.getData().getUserId();
        this.nickname = record.getData().getNickname();
        this.sports = record.getData().getSports();
        this.win = record.getData().getWin();
        this.lose = record.getData().getLose();
        this.draw = record.getData().getDraw();
        this.rating = record.getData().getRating();
        this.tier = record.getData().getTier();
    }
}
