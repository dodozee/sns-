package com.withsport.userservice.domain.user.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserProfileRequest {
    private String nickname;
    private String area;

}
