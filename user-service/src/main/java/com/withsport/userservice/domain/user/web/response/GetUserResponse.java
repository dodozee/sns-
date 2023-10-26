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
    private String email;
    private String name;
    private String nickname;
    private String area;

    public GetUserResponse(UserDto userDto){
        this.userId = userDto.getId();
        this.email = userDto.getEmail();
        this.name = userDto.getName();
        this.nickname = userDto.getNickname();
        this.area = userDto.getArea();
    }
}