package com.withsport.userservice.domain.user.web;


import com.withsport.userservice.domain.user.dto.UserDto;
import com.withsport.userservice.domain.user.service.UserService;
import com.withsport.userservice.global.dto.Result;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
//    연습삼아서 만들어본거
    @RequestMapping("/success")
    public String home() {
        System.out.println("login success!!!! 로그인 완료1!!!!!");
        return "login success!!!! 로그인 완료1!!!!!";
    }

    @GetMapping("/user")
    public ResponseEntity getUserByToken(@Valid @RequestHeader(value="user-id") String userId){

        UserDto userDto = userService.findUserByUserId(Long.parseLong(userId));

        GetUserByTokenResponse getUserByTokenResponse = new GetUserByTokenResponse(userDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(getUserByTokenResponse));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class GetUserByTokenResponse{
        private Long userId;
        private String email;
        private String name;
        private String nickname;
        private String address;

        public GetUserByTokenResponse(UserDto userDto){
            this.userId = userDto.getId();
            this.email = userDto.getEmail();
            this.name = userDto.getName();
            this.nickname = userDto.getNickname();
            this.address = userDto.getAddress();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") Long userId){

        UserDto userDto = userService.findUserByUserId(userId);

        GetUserResponse getUserResponse = new GetUserResponse(userDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(getUserResponse));
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class GetUserResponse{
        private Long userId;
        private String email;
        private String name;
        private String nickname;
        private String address;

        public GetUserResponse(UserDto userDto){
            this.userId = userDto.getId();
            this.email = userDto.getEmail();
            this.name = userDto.getName();
            this.nickname = userDto.getNickname();
            this.address = userDto.getAddress();
        }
    }

}
