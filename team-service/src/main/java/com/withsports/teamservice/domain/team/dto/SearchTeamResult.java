package com.withsports.teamservice.domain.team.dto;

import com.nimbusds.openid.connect.sdk.claims.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchTeamResult {
    private Long teamId;
    private String teamName;
    private Address address;
    private String teamImageUrl;
    private Long win;
    private Long draw;
    private Long lose;
}
