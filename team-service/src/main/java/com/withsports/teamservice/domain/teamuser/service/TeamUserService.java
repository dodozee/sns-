package com.withsports.teamservice.domain.teamuser.service;

import com.withsports.teamservice.domain.team.web.response.GetTeamUserResponse;
import com.withsports.teamservice.domain.teamuser.dto.TeamUserDetailDto;
import com.withsports.teamservice.domain.teamuser.dto.TeamUserApplicationDto;
import com.withsports.teamservice.domain.teamuser.entity.TeamUser;

import java.util.List;

public interface TeamUserService {



    void applyTeamUser(TeamUserApplicationDto teamUserDto);

    void withdrawTeamUser(Long teamId, Long userId);


    TeamUserDetailDto findTeamUserById(Long teamId);

    List<TeamUserDetailDto> findTeamUsersByTeamId(Long teamId);

    List<GetTeamUserResponse> findTeamFeignUsersByTeamId(Long teamId);

    TeamUser findTeamUserByTeamIdAndUserId(Long teamId, Long userId);

    TeamUser findTeamUserByUserIdAndSports(Long userId, String sports);

    List<Long> findTeamIdsByUserId(Long userId);
}
