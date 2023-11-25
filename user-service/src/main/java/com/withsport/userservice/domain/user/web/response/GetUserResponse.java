package com.withsport.userservice.domain.user.web.response;

import com.withsport.userservice.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse{
    private Long userId;
    private String name;
    private String nickname;
    private String profileImage;
    private String area;
    private String introduction;

    public GetUserResponse(UserDto userDto){
        this.userId = userDto.getId();
        this.name = userDto.getName();
        this.nickname = userDto.getNickname();
        this.profileImage = userDto.getProfileImage();
        this.area = userDto.getArea();
        this.introduction = userDto.getIntroduction();
    }
}