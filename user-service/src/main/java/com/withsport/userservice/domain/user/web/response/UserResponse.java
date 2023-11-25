package com.withsport.userservice.domain.user.web.response;

import com.withsport.userservice.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id; // 사용자 id
    private String nickname; // 사용자 닉네임
    private String area; // 사용자 지역
    private String introduction; // 사용자 소개
    private String profileImage; // 사용자 프로필 이미지

    public UserResponse(UserDto userDto) {
        this.id = userDto.getId();
        this.nickname = userDto.getNickname();
        this.area = userDto.getArea();
        this.introduction = userDto.getIntroduction();
        this.profileImage = userDto.getProfileImage();
    }

}
