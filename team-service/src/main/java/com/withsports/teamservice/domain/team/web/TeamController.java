package com.withsports.teamservice.domain.team.web;


import com.withsports.teamservice.domain.team.dto.TeamDto;
import com.withsports.teamservice.domain.team.dto.TeamsDto;
import com.withsports.teamservice.domain.team.dto.UpdateTeamProfileDto;
import com.withsports.teamservice.domain.team.service.S3Uploader;
import com.withsports.teamservice.domain.team.service.TeamService;
import com.withsports.teamservice.domain.team.web.request.UpdateTeamProfileRequest;
import com.withsports.teamservice.domain.team.web.response.*;
import com.withsports.teamservice.domain.teamuser.service.TeamUserService;
import com.withsports.teamservice.global.client.user.GetUserResponse;
import com.withsports.teamservice.global.client.user.UserClient;
import com.withsports.teamservice.global.dto.Result;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamUserService teamUserService;
    private final UserClient userClient;
    private final S3Uploader s3Uploader;
    //TODO FeignClient 테스트
    @GetMapping("/team/test/{userId}")
    ResponseEntity<Result> test(@PathVariable("userId") String userId) {

        Result<GetUserResponse> nicknameUserById = userClient.getNicknameUserById(Long.valueOf(userId));
        System.out.println(nicknameUserById.getData().getNickname());
        System.out.println(nicknameUserById.getData().getArea());
        System.out.println(nicknameUserById.getData().getProfileImage());
        System.out.println("팀 테스트 api 호출");
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(nicknameUserById));
    }

    //TODO 팀 프로필 조회 -COMPLETE
    @GetMapping("/team/{teamId}")
    ResponseEntity<Result> getTeamProfile(@PathVariable("teamId") String teamId) {
        System.out.println("팀 프로필 조회 api 호출");
        TeamDto teamDto = teamService.findTeamById(Long.valueOf(teamId));
        TeamDetailResponse teamDetailResponse = TeamDetailResponse.builder()
                .id(teamDto.getId())
                .teamName(teamDto.getTeamName())
                .leaderNickname(teamDto.getLeaderName())
                .introduction(teamDto.getIntroduction())
                .area(teamDto.getArea())
                .sports(teamDto.getSports())
                .imageUrl(teamDto.getImageUrl())
                .teamMemberCount(teamDto.getTeamMemberCount())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
            .body(Result.createSuccessResult(teamDetailResponse));
    }



    //TODO 팀 프로필 수정 -COMPLETE
    @PutMapping("/team/{teamId}")
    ResponseEntity<Result> updateTeamProfile(@PathVariable("teamId") String teamId,
                                                    @RequestPart(value = "UpdateTeamProfileRequest") UpdateTeamProfileRequest teamRequest,
                                                    @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        System.out.println("팀 프로필 수정 api 호출");
        UpdateTeamProfileDto profileDto = UpdateTeamProfileDto.builder()
                .id(Long.valueOf(teamId))
                .teamName(teamRequest.getTeamName())
                .introduction(teamRequest.getIntroduction())
                .sports(teamRequest.getSports())
                .area(teamRequest.getArea())
                .build();

        teamService.updateTeamProfile(profileDto, image);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult("팀 프로필 수정 완료"));
    }

//    //TODO 팀원 목록 조회
//    @GetMapping("/team/{teamId}/TeamUsers")
//    public ResponseEntity<Result> getTeamMembers(@PathVariable("teamId") String teamId) {
//        System.out.println("팀원 목록 조회 api 호출");
//        List<TeamUserDto> teamUserDtoList = teamUserService.findTeamUsers(Long.valueOf(teamId));
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(Result.createSuccessResult("팀원 목록 조회 완료"));
//    }

    //TODO Feign 프로필 사진 저장
    @PostMapping("/user/{userId}/profile")
    ResponseEntity<Result> saveProfileImage(@PathVariable("userId") String userId,
                                                   @RequestPart MultipartFile image) throws Exception {
        System.out.println("프로필 사진 저장 api 호출");
        String profile = s3Uploader.upload(Long.valueOf(userId), image, "profile");
        ProfileResponse response = ProfileResponse.of(profile);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }

    @Data @NoArgsConstructor
    static class ProfileResponse {
        private String profile;

        public ProfileResponse(String profile) {
            this.profile = profile;
        }

        static ProfileResponse of(String profile) {
            return new ProfileResponse(profile);
        }

    }

    //Feign
    @GetMapping("/team/feign/{teamId}")
    ResponseEntity<Result> getTeam(@PathVariable("teamId") String teamId) {
        System.out.println("팀 프로필 조회 feign api 호출");
        TeamDto teamDto = teamService.findTeamById(Long.valueOf(teamId));
        GetTeamResponse response = GetTeamResponse.builder()
                .id(teamDto.getId())
                .teamName(teamDto.getTeamName())
                .leaderId(teamDto.getLeaderId())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
            .body(Result.createSuccessResult(response));
    }

    //TODO 사용자가 속해 있는 팀 목록 조회
    @GetMapping("/team/teams")
    ResponseEntity<Result> getTeams(@RequestHeader("user-Id") String userId) {
        System.out.println("팀 목록 조회 api 호출");
        List<TeamsDto> teamsDto = teamService.findTeamsByUserId(Long.valueOf(userId));
        System.out.println("조회까지는 성공 teamsDto:"+teamsDto);
        List<TeamsResponse> response;
        if(teamsDto.size() == 0) {
            response = null;
        } else {
            response = teamsDto
                    .stream()
                    .map(TeamsResponse::new)
                    .toList();
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }

    //TODO 팀명으로 팀 검색
    @GetMapping("/team/teamname/{teamName}")
    ResponseEntity<Result> searchTeamByName(@PathVariable String teamName){
        System.out.println("팀명으로 팀 검색 api 호출");
        List<TeamDto> teamsDto = teamService.findTeamByName(teamName);
        List<TeamResponse> response;
        if(teamsDto.size() == 0){
            response = null;
        }else{
            response = teamsDto
                    .stream()
                    .map(TeamResponse::new)
                    .toList();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }

    @GetMapping("/feign/team/leaderId/{leaderId}/sports/{sports}")
    ResponseEntity<Result> getTeamByLeaderIdAndSportsId(@PathVariable("leaderId") String leaderId,
                                                        @PathVariable("sports") String sports){
        System.out.println("Feign 팀장 id와 스포츠 id로 팀 조회 api 호출");
        Long teamId = teamService.findTeamByLeaderIdAndSportsId(Long.valueOf(leaderId), sports);
        GetTeamIdResponse response = GetTeamIdResponse.builder()
                .teamId(teamId)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(response));
    }





}



