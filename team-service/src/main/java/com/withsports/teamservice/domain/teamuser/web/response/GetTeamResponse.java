package com.withsports.teamservice.domain.teamuser.web.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTeamResponse {
    private Long teamId;
    private String teamName;
    private String nickname;


}
