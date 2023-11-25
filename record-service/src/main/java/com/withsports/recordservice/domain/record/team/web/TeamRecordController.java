package com.withsports.recordservice.domain.record.team.web;


import com.withsports.recordservice.domain.record.matching.dto.TeamRecordDto;
import com.withsports.recordservice.domain.record.team.service.TeamRecordService;
import com.withsports.recordservice.domain.record.team.web.response.TeamRecordResponse;
import com.withsports.recordservice.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamRecordController {

    private final TeamRecordService teamRecordService;

    @GetMapping("/record/team/{teamId}")
    ResponseEntity<Result> getRecordByTeamId(@PathVariable("teamId") String teamId){

        TeamRecordDto teamRecordDto = teamRecordService.getRecordByTeamId(Long.valueOf(teamId));

        TeamRecordResponse teamRecordResponse = TeamRecordResponse.builder()
                .teamId(teamRecordDto.getTeamId())
                .teamName(teamRecordDto.getTeamName())
                .win(teamRecordDto.getWin())
                .lose(teamRecordDto.getLose())
                .draw(teamRecordDto.getDraw())
                .winRate(teamRecordDto.getWinRate())
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(teamRecordResponse));

    }
}
