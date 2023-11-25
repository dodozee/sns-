package com.withsports.teamservice.domain.team.service;

import com.withsports.teamservice.domain.team.dto.*;
import com.withsports.teamservice.domain.team.dto.producer.KafkaProduceUpdateUserProfileDto;
import com.withsports.teamservice.domain.team.web.response.CreateTeamResponse;
import com.withsports.teamservice.domain.teamuser.dto.TeamUserApplicationDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeamService {

    //팀 생성 및 수정
    CreateTeamResponse createTeam(CreateTeamDto createTeamDto, MultipartFile image) throws Exception;

    void isValidTeamLeader(Long leaderId,  String sports);

    //팀 삭제
    void deleteTeam(Long teamId, Long leaderId) throws Exception;
    //
    SliceImpl<SearchTeamResult> findSearchTeamScroll(SearchTeamCondition condition, Pageable pageable);
    //
    List<SearchTeamResult> findFavoriteTeam(SearchTeamCondition condition, Long userId);
    //
    TeamDto findTeamById(Long teamId);

//    TeamByUserIdDto findTeamByUserId(Long userId);
    List<TeamDto> findTeamAllById(Iterable<Long> teamIds);

    void checkDuplicateTeamName(String teamName);


    void isTeamLeader(Long leaderId, Long teamId);

    void deleteTeamUser(Long teamId, Long userId, Long leaderId);

    void updateTeamProfile(UpdateTeamProfileDto profileDto, MultipartFile image) throws IOException;

    void acceptTeamUser(Long teamId, Long userId, Long leaderId);

    void rejectTeamUser(Long teamId, Long userId, Long leaderId);

    List<TeamUserApplicationDto> getTeamUserApplication(Long teamdId, Long leaderId);

    List<TeamsDto> findTeamsByUserId(Long userId);

    List<TeamDto> findTeamByName(String teamName);

    void updateUserTeam(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto);

    Long findTeamByLeaderIdAndSportsId(Long leaderId, String sports);
}
