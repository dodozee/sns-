package com.withsport.userservice.domain.jwt.web;


import com.withsport.userservice.domain.jwt.service.AccessTokenService;
import com.withsport.userservice.domain.jwt.service.RefreshTokenService;
import com.withsport.userservice.domain.jwt.web.response.RefreshTokenResponse;
import com.withsport.userservice.domain.user.dto.JwtTokenDto;
import com.withsport.userservice.domain.user.service.UserService;
import com.withsport.userservice.global.dto.Result;
import com.withsport.userservice.global.utils.CookieProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApi {

    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;
    private final CookieProvider cookieProvider;
    private final UserService userService;

    @GetMapping("/reissue")
    public ResponseEntity<Result> refreshToken(@RequestHeader(value = "X-AUTH-TOKEN") String accessToken,
                                               @CookieValue(value = "refresh-token") String refreshToken) {


        JwtTokenDto jwtTokenDto = refreshTokenService.refreshJwtToken(accessToken, refreshToken);

        ResponseCookie responseCookie = cookieProvider.createRefreshTokenCookie(refreshToken);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(Result.createSuccessResult(new RefreshTokenResponse(jwtTokenDto)));
    }

    @PostMapping("/logout")
    public ResponseEntity<Result> logout(@RequestHeader("X-AUTH-TOKEN") String accessToken){
        refreshTokenService.logoutToken(accessToken);

        ResponseCookie responseCookie = cookieProvider.deleteRefreshTokenCookie();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(Result.createSuccessResult(""));
    }

    @GetMapping(value = "/check/access-token")
    public ResponseEntity<Result> checkAccessToken(@RequestHeader(name = "Authorization") String authorization) {
        System.out.println("999999999999999");
        accessTokenService.checkAccessToken(authorization);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(null));
    }




}
