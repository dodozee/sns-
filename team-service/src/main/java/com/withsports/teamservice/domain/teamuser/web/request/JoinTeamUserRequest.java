package com.withsports.teamservice.domain.teamuser.web.request;

import com.withsports.teamservice.domain.teamuser.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinTeamUserRequest {

    private Long teamId;
    private String introduction;



}
