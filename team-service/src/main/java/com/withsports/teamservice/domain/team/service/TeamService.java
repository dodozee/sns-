package com.withsports.teamservice.domain.team.service;

import com.withsports.teamservice.domain.team.dto.CreateTeamDto;
import com.withsports.teamservice.domain.team.dto.SearchTeamCondition;
import com.withsports.teamservice.domain.team.dto.SearchTeamResult;
import com.withsports.teamservice.domain.team.dto.TeamDto;
import com.withsports.teamservice.global.entity.Sports;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public interface TeamService {

    //팀 생성 및 수정
    void createTeam(CreateTeamDto createTeamDto);

    Boolean isValidTeamLeader(Long leaderId,  String sports);

    //팀 삭제
    TeamDto deleteTeam(Long teamId);
    //
    SliceImpl<SearchTeamResult> findSearchTeamScroll(SearchTeamCondition condition, Pageable pageable);
    //
    List<SearchTeamResult> findFavoriteTeam(SearchTeamCondition condition, Long userId);
    //
    TeamDto findTeamById(Long teamId);

//    TeamByUserIdDto findTeamByUserId(Long userId);
    List<TeamDto> findTeamAllById(Iterable<Long> teamIds);

    void checkDuplicateTeamName(String teamName);
}
