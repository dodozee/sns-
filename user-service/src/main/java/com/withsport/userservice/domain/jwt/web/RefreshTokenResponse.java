package com.withsport.userservice.domain.jwt.web;

import com.withsport.userservice.domain.user.dto.JwtTokenDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponse {
    private String accessToken;
    private String expiredTime;

    public RefreshTokenResponse(JwtTokenDto jwtTokenDto){
        this.accessToken = jwtTokenDto.getAccessToken();
        this.expiredTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(jwtTokenDto.getAccessTokenExpiredDate());
    }
}
