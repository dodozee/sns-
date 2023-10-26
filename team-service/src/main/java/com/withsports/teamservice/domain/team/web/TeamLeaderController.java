package com.withsports.teamservice.domain.team.web;


import com.withsports.teamservice.domain.team.dto.CreateTeamDto;
import com.withsports.teamservice.domain.team.service.TeamService;
import com.withsports.teamservice.domain.team.web.request.TeamRequest;
import com.withsports.teamservice.global.dto.Result;
import com.withsports.teamservice.global.entity.Sports;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TeamLeaderController {

    private final TeamService teamService;


    @GetMapping("/team/create/{teamName}")
    public ResponseEntity<Result> checkDuplicateTeamName(@PathVariable String teamName) {

        teamService.checkDuplicateTeamName(teamName);

        return ResponseEntity.ok(Result.createSuccessResult("사용 가능한 팀 이름입니다."));
    }

    @PostMapping("/team")
    public ResponseEntity createTeam(@Valid @RequestBody TeamRequest teamRequest,
                                             @RequestHeader("user-id") String userId) {


        CreateTeamDto createTeamDto = CreateTeamDto.builder()
                .teamName(teamRequest.getTeamName())
                .sports(Sports.valueOf(teamRequest.getSports()))
                .leaderId(Long.parseLong(userId))
                .build();

        teamService.createTeam(createTeamDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Result.success());
    }

    //TODO 팀원 신청 수락


    //TODO 팀원 신청 거절

    //TODO 팀원 제명

    //


}
