package com.withsport.userservice.domain.jwt.service;


import com.withsport.userservice.domain.jwt.exception.AccessTokenNotValidException;
import com.withsport.userservice.global.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final JwtTokenProvider tokenProvider;

    public void checkAccessToken(String authorizationHeader){

        String token = authorizationHeader.replace("Bearer ", "");

        if(!tokenProvider.validateJwtToken(token)){
            throw new AccessTokenNotValidException("Access Token is not Valid");
        }
    }
}
