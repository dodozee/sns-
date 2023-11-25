package com.withsports.teamservice.domain.teamuser.service;

import com.withsports.teamservice.domain.team.entity.Team;
import com.withsports.teamservice.domain.team.exception.ExistSameTeamException;
import com.withsports.teamservice.domain.team.exception.NotExistTeamException;
import com.withsports.teamservice.domain.team.repository.TeamRepository;
import com.withsports.teamservice.domain.team.web.response.GetTeamUserResponse;
import com.withsports.teamservice.domain.teamuser.dto.TeamUserDetailDto;
import com.withsports.teamservice.domain.teamuser.dto.TeamUserApplicationDto;
import com.withsports.teamservice.domain.teamuser.entity.Role;
import com.withsports.teamservice.domain.teamuser.entity.TeamUser;
import com.withsports.teamservice.domain.teamuser.repository.TeamUserRepository;
import com.withsports.teamservice.global.client.record.RecordClient;
import com.withsports.teamservice.global.client.record.UserRecordResponse;
import com.withsports.teamservice.global.client.user.GetUserResponse;
import com.withsports.teamservice.global.client.user.UserClient;
import com.withsports.teamservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class TeamUserServiceImpl implements TeamUserService {

    private final TeamUserRepository teamUserRepository;
    private final TeamRepository teamRepository;
    private final UserClient userClient;
    private final RecordClient recordClient;


    //TODO 팀원 신청
    @Transactional
    @Override
    public void applyTeamUser(TeamUserApplicationDto teamUserApplicationDto) {
        validateDuplicateSameTeam(teamUserApplicationDto.getTeamId(), teamUserApplicationDto.getUserId());
        validateDuplicateSportsAndUser(teamUserApplicationDto.getTeamId(), teamUserApplicationDto.getUserId());

        Team team = teamRepository.findById(teamUserApplicationDto.getTeamId())
                .orElseThrow(() -> new NotExistTeamException("해당 팀이 없습니다. id=" + teamUserApplicationDto.getTeamId()));
        Result<GetUserResponse> response = userClient.getNicknameUserById(teamUserApplicationDto.getUserId());
        System.out.println("==========userClient 호출 후 =============");
        System.out.println(response.getData().getNickname());
        System.out.println("==========userClient 호출 후 =============");

        String nickname = response.getData().getNickname();
        TeamUser teamUser = TeamUser.of(teamUserApplicationDto.getUserId(), nickname, Role.NORMAL, teamUserApplicationDto.getIntroduction());
//        teamUser.setTeam(team);
        team.addTeamUser(teamUser);
        teamUserRepository.save(teamUser);
        teamUser.placed();
        //TODO 팀원 신청 알림
//        teamUserRepository.save(teamUser);
    }

    @Transactional
    @Override
    public void withdrawTeamUser(Long teamId, Long userId) {
        teamRepository.findById(teamId)
                .orElseThrow(() -> new NotExistTeamException("존재하지 않는 팀입니다."));

        teamUserRepository.deleteTeamUserByTeamIdAndUserId(teamId,userId);

    }


    @Override
    public TeamUserDetailDto findTeamUserById(Long teamId) {


        return null;
    }

//    private Double winRate; ->Record에서
//    private String tier; -> Record에서
//    private Long mvpCount; -> Record에서 or vote에



//    private String role; -> teamUser에서 v
//    private Long teamId;
//    private Long userId;
//    private String nickname; -> teamUser에서 v
//    private String area; -> User에서
//    private String profileImage; -> User에서
//    private String introduction; -> User에서




    @Override
    public List<TeamUserDetailDto> findTeamUsersByTeamId(Long teamId) {

        List<TeamUser> teamUserList = teamUserRepository.findByTeamId(teamId);
        List<TeamUserDetailDto> teamUserResponse = new ArrayList<>();

        for (TeamUser teamUser : teamUserList) {
            TeamUserDetailDto teamUserDetailDto = new TeamUserDetailDto();

            teamUserDetailDto.setTeamId(teamUser.getTeam().getId());
            teamUserDetailDto.setUserId(teamUser.getUserId());
            teamUserDetailDto.setNickname(teamUser.getNickname());
            teamUserDetailDto.setRole(teamUser.getRole().name());

            Result<GetUserResponse> userResponse = userClient.getNicknameUserById(teamUser.getUserId());
            Result<UserRecordResponse> recordByUserIdAndSports = recordClient.getRecordByUserIdAndSports(String.valueOf(teamUser.getUserId()), teamUser.getTeam().getSports());
            teamUserDetailDto.setIntroduction(userResponse.getData().getIntroduction());
            teamUserDetailDto.setProfileImage(userResponse.getData().getProfileImage());
            teamUserDetailDto.setArea(userResponse.getData().getArea());

            teamUserDetailDto.setMvpCount(0L);
            teamUserDetailDto.setTier(recordByUserIdAndSports.getData().getTier());
            teamUserDetailDto.setWinRate(winRate(recordByUserIdAndSports.getData()));

            teamUserResponse.add(teamUserDetailDto);
        }
        return teamUserResponse;


        /**
         *
         * public List<TeamUserDetailDto> getTeamUserDetailsByTeamId(Long teamId) {
         *     // 팀의 유저 목록을 가져옵니다.
         *     List<TeamUser> teamUserList = teamUserRepository.findByTeamId(teamId);
         *
         *     List<TeamUserDetailDto> teamUserDetailList = new ArrayList<>();
         *
         *     for (TeamUser teamUser : teamUserList) {
         *         TeamUserDetailDto teamUserDetail = new TeamUserDetailDto();
         *
         *         // TeamUser에서 필요한 정보를 설정합니다.
         *         teamUserDetail.setNickname(teamUser.getNickname());
         *         teamUserDetail.setRole(teamUser.getRole());
         *
         *         // UserRepositroy와 RecordRepositroy를 사용하여 추가 정보를 설정합니다.
         *         User user = userRepository.findById(teamUser.getUserId());
         *         Record record = recordRepository.findByUserId(teamUser.getUserId());
         *
         *         if (user != null) {
         *             teamUserDetail.setIntroduction(user.getIntroduction());
         *             teamUserDetail.setProfileImage(user.getProfileImage());
         *             teamUserDetail.setArea(user.getArea());
         *         }
         *
         *         if (record != null) {
         *             teamUserDetail.setWinRate(record.getWinRate());
         *             teamUserDetail.setTier(record.getTier());
         *             teamUserDetail.setMvpCount(record.getMvpCount());
         *         }
         *
         *         teamUserDetailList.add(teamUserDetail);
         *     }
         *
         *     return teamUserDetailList;
         * }
         */


    }

    private Double winRate(UserRecordResponse data) {
        Long win = data.getWin();
        Long lose = data.getLose();
        Long draw = data.getDraw();
        Long total = win + lose + draw;
        if(total == 0){
            return 0.0;
        }
        return (double) win / total;
    }


    @Override
    public List<GetTeamUserResponse> findTeamFeignUsersByTeamId(Long teamId) {

        List<TeamUser> teamUserList = teamUserRepository.findByTeamId(teamId);

        return teamUserList.stream()
                .map(teamUser -> {
                    GetTeamUserResponse response = new GetTeamUserResponse();
                    response.setTeamId(teamUser.getTeam().getId());
                    response.setUserId(teamUser.getUserId());
                    response.setId(teamUser.getId());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TeamUser findTeamUserByTeamIdAndUserId(Long teamId, Long userId) {
        List<TeamUser> byTeamId = teamUserRepository.findByTeamId(teamId);

        return null;
    }

    @Override
    public TeamUser findTeamUserByUserIdAndSports(Long userId, String sports) {
        return teamUserRepository.findTeamUserByUserIdAndSports(userId, sports).orElseThrow(() -> new NotExistTeamException("해당 스포츠 팀이 없습니다. id=" + userId + ", sports ="+ sports));
    }

    @Override
    public List<Long> findTeamIdsByUserId(Long userId) {
        return teamUserRepository.findTeamIdsByUserId(userId);
    }


    private void validateDuplicateSportsAndUser(Long teamId, Long userId){
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NotExistTeamException("해당 팀이 없습니다. id=" + teamId));

        String sports = team.getSports();

        List<TeamUser> teamUsers = teamUserRepository.findByUserId(userId);

        //하나씩 꺼내서 동일한 스포츠 종목이 있는지 비교
        for(TeamUser teamUser : teamUsers){
            if(teamRepository.findById(teamUser.getTeam().getId()).equals(sports)){
                throw new ExistSameTeamException("이미 해당 종목의 팀에 속해있습니다.");
            }
        }
    }

    private void validateDuplicateSameTeam(Long teamId, Long userId) {
        teamRepository.findById(teamId)
                .orElseThrow(() -> new NotExistTeamException("해당 팀이 없습니다. id=" + teamId));
        List<TeamUser> teamUsers = teamUserRepository.findByUserId(userId);
        //하나씩 꺼내서 동일한 팀이 있는지 비교
        for(TeamUser teamUser : teamUsers){
            System.out.println("teamUser.getTeam().getId() = " + teamUser.getTeam().getId());
            if(teamUser.getTeam().getId().equals(teamId)){
                throw new ExistSameTeamException("이미 해당 팀에 속해있습니다.");
            }
        }

    }


}
