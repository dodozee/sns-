package com.withsports.teamservice.domain.teamuser.web;

import com.withsports.teamservice.domain.team.dto.TeamDto;
import com.withsports.teamservice.domain.team.service.TeamService;
import com.withsports.teamservice.domain.team.web.response.GetTeamUserListResponse;
import com.withsports.teamservice.domain.team.web.response.GetTeamUserResponse;
import com.withsports.teamservice.domain.teamuser.dto.TeamUserApplicationDto;
import com.withsports.teamservice.domain.teamuser.dto.TeamUserDetailDto;
import com.withsports.teamservice.domain.teamuser.entity.TeamUser;
import com.withsports.teamservice.domain.teamuser.service.TeamUserService;
import com.withsports.teamservice.domain.teamuser.web.request.JoinTeamUserRequest;
import com.withsports.teamservice.domain.teamuser.web.response.GetTeamIdsResponse;
import com.withsports.teamservice.domain.teamuser.web.response.GetTeamResponse;
import com.withsports.teamservice.domain.teamuser.web.response.TeamUserDetailResponse;
import com.withsports.teamservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TeamUserController {

    private final TeamUserService teamUserService;
    private final TeamService teamService;

    //TODO 팀원 가입 신청
    @PostMapping("/teamuser")
    public ResponseEntity applyTeamUser(@RequestBody JoinTeamUserRequest joinTeamUserRequest,
                                        @RequestHeader(value = "user-id") String userId) {
        System.out.println("팀원 가입 신청 api 호출");
        TeamUserApplicationDto teamUserDto = TeamUserApplicationDto.builder()
                .teamId(joinTeamUserRequest.getTeamId())
                .userId(Long.valueOf(userId))
                .introduction(joinTeamUserRequest.getIntroduction())
                .build();
        teamUserService.applyTeamUser(teamUserDto);
        Long teamId = teamUserDto.getTeamId();

        return ResponseEntity.status(HttpStatus.OK).body(Result.createSuccessResult(teamId));
    }

    //TODO 팀원 탈퇴
    @DeleteMapping("/teamuser/{teamId}")
    public ResponseEntity deleteTeamUser(@PathVariable String teamId,
                                         @RequestHeader(value = "user-id") String userId){

        teamUserService.withdrawTeamUser(Long.valueOf(teamId),Long.valueOf(userId));
        return ResponseEntity.status(HttpStatus.OK).body(Result.createSuccessResult("팀원 탈퇴 성공"));
    }


    //TODO 팀원 목록 조회
    @GetMapping("/teamusers/{teamId}")
    public ResponseEntity<Result> getTeamUsers(@PathVariable("teamId") String teamId){
        List<TeamUserDetailDto> teamUserDtoList = teamUserService.findTeamUsersByTeamId(Long.valueOf(teamId));
        List<TeamUserDetailResponse> responses = teamUserDtoList.stream()
                .map(TeamUserDetailResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(responses));
    }

    /**
     *  List<StoreDto> storeDtoList = storeService.findStoreAllById(storeIds);
     *
     *         List<GetStoreResponse> responses = storeDtoList.stream()
     *                 .map(GetStoreResponse::new)
     *                 .collect(Collectors.toList());
     */


    //TODO 팀원 상세 조회
    @GetMapping("/teamuser/{teamUserId}")
    ResponseEntity<Result> getTeamUser(@PathVariable String teamUserId){

        TeamUserDetailDto detailDto = teamUserService.findTeamUserById(Long.valueOf(teamUserId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(detailDto));
    }

    @GetMapping("/teamuser/feign/{teamId}/{userId}")
    ResponseEntity<Result> getTeamUserFeign(@PathVariable(value = "teamId") String teamId,
                                            @PathVariable(value = "userId") String userId) {
        TeamUser teamUser = teamUserService.findTeamUserByTeamIdAndUserId(Long.valueOf(teamId), Long.valueOf(userId));
        GetTeamUserResponse response = GetTeamUserResponse.builder()
                .id(teamUser.getId())
                .teamId(teamUser.getTeam().getId())
                .userId(teamUser.getUserId())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));

    }

    //TODO 입력한 종목이 자기가 속한 종목인지 확인
    @GetMapping("/matching/teamuser/{sports}")
    ResponseEntity<Result> checkByUserIdAndSports(@PathVariable("sports") String sports,
                                               @RequestHeader("user-id") String userId){
        TeamUser teamUser = teamUserService.findTeamUserByUserIdAndSports(Long.valueOf(userId), sports);
        GetTeamUserResponse response = GetTeamUserResponse.builder()
                .id(teamUser.getId())
                .teamId(teamUser.getTeam().getId())
                .userId(teamUser.getUserId())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }


    @GetMapping("/teamuser/feign/{teamId}")
    ResponseEntity<Result> getTeamUserListFeign(@PathVariable(value = "teamId") String teamId){

        TeamDto team = teamService.findTeamById(Long.valueOf(teamId));
        GetTeamUserListResponse response = GetTeamUserListResponse.builder()
                .id(team.getId())
                .teamName(team.getTeamName())
                .teamUserList(teamUserService.findTeamFeignUsersByTeamId(Long.valueOf(teamId)))
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }

    @GetMapping("/teamuser/{userId}/sports/{sports}")
    ResponseEntity<Result> getTeamIdByUserIdAndSports(@PathVariable("userId") String userId,
                                                       @PathVariable("sports") String sports){

        TeamUser teamUser = teamUserService.findTeamUserByUserIdAndSports(Long.valueOf(userId), sports);
        GetTeamResponse response = GetTeamResponse.builder()
                .teamId(teamUser.getTeam().getId())
                .teamName(teamUser.getTeam().getTeamName())
                .nickname(teamUser.getNickname())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }

    @GetMapping("/teamuser/feign/user/{userId}")
    ResponseEntity<Result> getTeamIdByUserId(@PathVariable("userId") String userId){
        List<Long> teamIds = teamUserService.findTeamIdsByUserId(Long.valueOf(userId));
        System.out.println("teamIds = " + teamIds);
        for(Long teamId : teamIds){
            System.out.println("teamId = " + teamId);
        }
        GetTeamIdsResponse response = GetTeamIdsResponse.builder()
                .teamIds(teamIds)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }


}
