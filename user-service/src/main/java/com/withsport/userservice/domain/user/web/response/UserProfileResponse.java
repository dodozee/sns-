package com.withsport.userservice.domain.user.web.response;



import com.withsport.userservice.domain.user.dto.UserDto;
import com.withsport.userservice.domain.user.dto.UserRecordResponse;
import com.withsport.userservice.global.client.record.TeamRecordResponse;
import lombok.*;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
//TODO 사용자 프로필 조회(유저아이디, 닉네임, 소개, 지역, 이미지, 티어, 승률)
public class UserProfileResponse {
    private Long userId;
    private String nickname;
    private String introduction;
    private String area;
    private String imageUrl;
    private String tier;
    private Long rating;
    private Long win;
    private Long lose;
    private Long draw;
    private Double winRate;
//    private Long mvpCount;

    public UserProfileResponse(UserDto userDto, UserRecordResponse response) {
        this.userId = userDto.getId();
        this.nickname = userDto.getNickname();
        this.introduction = userDto.getIntroduction();
        this.area = userDto.getArea();
        this.imageUrl = userDto.getProfileImage();
//        Total loop = loop(teamRecordResponse);
        this.win = response.getWin();
        this.lose = response.getLose();
        this.draw = response.getDraw();
        if((response.getWin() + response.getLose() + response.getDraw())==0){
            this.winRate=0.0d;
        }else{
            this.winRate = ((double) response.getWin() / (response.getWin() + response.getLose() + response.getDraw()));

        }
        this.tier = response.getTier();
        this.rating = response.getRating();
//        this.win = teamRecordResponse.get(0).getWin()+ teamRecordResponse.get(1).getWin()+ teamRecordResponse.get(2).getWin();
//        this.lose = teamRecordResponse.get(0).getLose()+ teamRecordResponse.get(1).getLose()+ teamRecordResponse.get(2).getLose();
//        this.draw = teamRecordResponse.get(0).getDraw()+ teamRecordResponse.get(1).getDraw()+ teamRecordResponse.get(2).getDraw();
//        this.winRate = (double) win / (win + lose + draw);
    }

    private Total loop(List<TeamRecordResponse> teamRecordResponse) {
            Long totalWins = 0L;
            Long totalLoses = 0L;
            Long totalDraws = 0L;
            for(TeamRecordResponse record : teamRecordResponse){
                totalWins += record.getWin();
                totalLoses += record.getLose();
                totalDraws += record.getDraw();
            }

            return new Total(totalWins, totalLoses, totalDraws);

    }

    @Data @NoArgsConstructor
    static class Total{
        private Long totalWins;
        private Long totalLoses;
        private Long totalDraws;
        private Double winRate;

        public Total(Long totalWins, Long totalLoses, Long totalDraws){
            this.totalWins = totalWins;
            this.totalLoses = totalLoses;
            this.totalDraws = totalDraws;
            this.winRate = (double) totalWins / (totalWins + totalLoses + totalDraws);
        }


    }

    public UserProfileResponse(UserDto userDto) {
        this.userId = userDto.getId();
        this.nickname = userDto.getNickname();
        this.introduction = userDto.getIntroduction();
        this.area = userDto.getArea();
        this.imageUrl = userDto.getProfileImage();
        this.win = 0L;
        this.lose = 0L;
        this.draw = 0L;
        this.winRate = 0d;
    }

//    public UserProfileResponse(TeamRecordResponse teamRecordResponse) {
//        this.nickname = teamRecordResponse.getNickname();
//        this.introduction = teamRecordResponse.getIntroduction();
//        this.area = teamRecordResponse.getArea();
//        this.imageUrl = teamRecordResponse.getProfileImage();
//        this.tier = teamRecordResponse.getTier();
//        this.win = teamRecordResponse.getWin();
//        this.lose = teamRecordResponse.getLose();
//        this.draw = teamRecordResponse.getDraw();
//        this.winRate = teamRecordResponse.getWinRate();
//        this.mvpCount = teamRecordResponse.getMvpCount();
//    }
}
