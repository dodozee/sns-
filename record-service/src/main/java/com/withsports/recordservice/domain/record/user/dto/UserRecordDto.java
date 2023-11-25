package com.withsports.recordservice.domain.record.user.dto;

import com.withsports.recordservice.domain.record.user.entity.UserRecord;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRecordDto {
    private Long userId;
    private String nickname;
    private String sports;
    private Long win;
    private Long lose;
    private Long draw;
    private Long rating;
    private String tier;


    public static UserRecordDto of(UserRecord userRecord){
        return new UserRecordDto(
                userRecord.getUserId(),
                userRecord.getNickname(),
                userRecord.getSports(),
                userRecord.getWin(),
                userRecord.getLose(),
                userRecord.getDraw(),
                userRecord.getRating(),
                userRecord.getTier().name());

 }
}
