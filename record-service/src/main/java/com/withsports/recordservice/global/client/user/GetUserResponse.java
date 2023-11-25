package com.withsports.recordservice.global.client.user;


import com.withsports.recordservice.domain.record.user.entity.Tier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {
    private Long userId;
    private String nickname;
    private String profileImage;
    private String introduction;
    private String area;

}
