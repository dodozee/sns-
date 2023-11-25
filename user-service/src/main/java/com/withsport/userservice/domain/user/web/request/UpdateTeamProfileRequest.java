package com.withsport.userservice.domain.user.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTeamProfileRequest {
    private String teamName;
    private String sports;
    private String area;
    private String introduction;
}
