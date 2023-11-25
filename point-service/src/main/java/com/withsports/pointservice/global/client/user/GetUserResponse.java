package com.withsports.pointservice.global.client.user;


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
