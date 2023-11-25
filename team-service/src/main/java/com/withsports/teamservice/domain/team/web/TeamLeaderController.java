package com.withsports.teamservice.domain.team.web;


import com.withsports.teamservice.domain.team.dto.CreateTeamDto;
import com.withsports.teamservice.domain.team.service.TeamService;
import com.withsports.teamservice.domain.team.web.request.CreateTeamRequest;
import com.withsports.teamservice.domain.team.web.response.CreateTeamResponse;
import com.withsports.teamservice.domain.team.web.response.TeamUserApplicationResponse;
import com.withsports.teamservice.domain.teamuser.dto.TeamUserApplicationDto;
import com.withsports.teamservice.global.dto.Result;
import com.withsports.teamservice.global.entity.Sports;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TeamLeaderController {

    private final TeamService teamService;



    //TODO 팀명 체크
    @GetMapping("/team/check/{teamName}")
    ResponseEntity<Result> checkDuplicateTeamName(@PathVariable String teamName) {

        teamService.checkDuplicateTeamName(teamName);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("사용 가능한 팀 이름입니다."));
    }


    //TODO 팀 생성
    @PostMapping("/team")
    ResponseEntity<Result> createTeam(@Valid @RequestPart(value = "CreateTeamRequest") CreateTeamRequest teamRequest,
                                             @RequestPart(value = "image", required = false) MultipartFile image,
                                             @RequestHeader("user-id") String userId) throws Exception {

        System.out.println("createTeamRequest:"+teamRequest);
        System.out.println("createTeamRequest.getTeamName():"+teamRequest.getTeamName());
        System.out.println("createTeamRequest.getSports():"+teamRequest.getSports());
        System.out.println("createTeamRequest.getArea():"+teamRequest.getArea());
        System.out.println("createTeamRequest.getIntroduction():"+teamRequest.getIntroduction());

        System.out.println("userId:"+userId);

        System.out.println("image:"+image);
        System.out.println("image.getContentType():"+image.getContentType());
        System.out.println("image.getOriginalFilename():"+image.getOriginalFilename());
        System.out.println("image.getSize():"+image.getSize());
        System.out.println("image.toString():"+image.toString());


        CreateTeamDto createTeamDto = CreateTeamDto.builder()
                .teamName(teamRequest.getTeamName())
                .introduction(teamRequest.getIntroduction())
                .sports(teamRequest.getSports())
                .leaderId(Long.parseLong(userId))
                .area(teamRequest.getArea())
                .build();

        CreateTeamResponse response = teamService.createTeam(createTeamDto, image);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.createSuccessResult(response));
    }

    //TODO 팀 삭제
    @DeleteMapping("/team/{teamId}")
    ResponseEntity<Result> deleteTeam(@PathVariable String teamId,
                                             @RequestHeader("user-id") String leaderId) throws Exception {

        teamService.deleteTeam(Long.valueOf(teamId),Long.valueOf(leaderId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("팀이 삭제되었습니다."));
    }

    //TODO 팀 생성하는 팀 리더 자격 가능 확인
    @GetMapping("/team/validation/teamLeader/{sports}")
    ResponseEntity<Result> isValidTeamLeader(@PathVariable String sports,
                                                    @RequestHeader("user-id") String leaderId){

        System.out.println("팀 생성하려는 leaderId = " + leaderId);
        teamService.isValidTeamLeader(Long.valueOf(leaderId),sports);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("팀 리더 자격이 확인되었습니다."));
    }


    //TODO 팀원 신청 목록 조회
    @GetMapping("/team/{teamId}/application")
    ResponseEntity<Result> getTeamUserApplication(@PathVariable String teamId,
                                                  @RequestHeader("user-id") String leaderId){

        List<TeamUserApplicationResponse> response = teamService.getTeamUserApplication(Long.valueOf(teamId),Long.valueOf(leaderId))
                .stream()
                .map(TeamUserApplicationResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response,"신청 목록 조회"));
    }


    //TODO 팀원 신청 수락
    @PutMapping("/team/{teamId}/teamUser/acceptance/{userId}")
    ResponseEntity<Result> acceptTeamUser(@PathVariable String teamId,
                                                 @PathVariable String userId,
                                                 @RequestHeader("user-id") String leaderId){
        teamService.acceptTeamUser(Long.valueOf(teamId),Long.valueOf(userId),Long.valueOf(leaderId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(userId+"팀원이 수락 되었습니다."));
    }

    //TODO 팀원 신청 거절
    @PutMapping("/team/{teamId}/teamUser/rejection/{userId}")
    public ResponseEntity<Result> rejectTeamUser(@PathVariable String teamId,
                                                 @PathVariable String userId,
                                                 @RequestHeader("user-id") String leaderId){
        teamService.rejectTeamUser(Long.valueOf(teamId),Long.valueOf(userId),Long.valueOf(leaderId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(userId+"팀원이 거절 되었습니다."));
    }

    //TODO 팀원 제명
    @DeleteMapping("/team/{teamId}/teamUser/{userId}")
    public ResponseEntity<Result> deleteTeamUser(@PathVariable String teamId,
                                                 @PathVariable String userId,
                                                 @RequestHeader("user-id") String leaderId){
        teamService.deleteTeamUser(Long.valueOf(teamId),Long.valueOf(userId),Long.valueOf(leaderId));

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(userId+"팀원이 제명되었습니다."));
    }

    //TODO 본인이 팀장인지 체크
    @GetMapping("/check/validation/teamLeader/{teamId}")
public ResponseEntity<Result> isValidTeamLeader(@RequestHeader("user-id") String leaderId,
                                                @PathVariable Long teamId){
        teamService.isTeamLeader(Long.valueOf(leaderId),teamId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("팀 리더 자격이 확인되었습니다."));
    }


}
