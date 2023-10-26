package com.withsport.userservice.domain.user.web;


import com.withsport.userservice.domain.user.dto.UserDto;
import com.withsport.userservice.domain.user.service.UserService;
import com.withsport.userservice.domain.user.web.request.AddProfileRequest;
import com.withsport.userservice.domain.user.web.response.GetUserResponse;
import com.withsport.userservice.global.dto.Result;
import jakarta.servlet.http.HttpServletRequest;
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
//    @RequestMapping("/success")
//    public String home() {
//        System.out.println("login success!!!! 로그인 완료1!!!!!");
//        return "login success!!!! 로그인 완료1!!!!!";
//    }

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
            this.address = userDto.getArea();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getUser(@PathVariable("userId") Long userId){

        UserDto userDto = userService.findUserByUserId(userId);

        GetUserResponse getUserResponse = new GetUserResponse(userDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(getUserResponse));
    }



    //TODO 닉네임 중복 검사
    @GetMapping("/signup/check/nickname/")
    public ResponseEntity checkNickname(HttpServletRequest request){
        String nickname = request.getParameter("nickname");
        userService.checkDuplicateUserNickname(nickname);
        System.out.println("중복되는 닉네임이 없습니다.");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("중복되는 닉네임이 없습니다."));
    }

    //TODO 회원정보 추가 정보 받기
    @PutMapping("/signup/profile")
    public ResponseEntity  addProfile(@Valid @RequestHeader(value="user-id") String userId,
                                              @RequestBody AddProfileRequest addProfileRequest){
        String nickname = addProfileRequest.getNickname();
        String area = addProfileRequest.getArea();
        userService.addUserProfile(Long.parseLong(userId), nickname, area);
        System.out.println("회원정보 추가 정보를 저장했습니다.");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("회원정보 추가 정보를 저장했습니다."));
    }



}
