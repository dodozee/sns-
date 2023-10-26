package com.withsports.teamservice.domain.team.web;


import com.withsports.teamservice.domain.team.dto.TeamDto;
import com.withsports.teamservice.domain.team.service.TeamService;
import com.withsports.teamservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    //TODO 팀 프로필 조회
    @GetMapping("/team/{teamId}")
    public ResponseEntity<Result> getTeamProfile(@PathVariable("teamId") Long teamId) {
        TeamDto teamDto = teamService.findTeamById(teamId);

    return ResponseEntity.status(HttpStatus.OK)
            .body(Result.createSuccessResult());
    }
    //TODO 팀 프로필 수정

    //TODO 팀원 목록 조회
}
