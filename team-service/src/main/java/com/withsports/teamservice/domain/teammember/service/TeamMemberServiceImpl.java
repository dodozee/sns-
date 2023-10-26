package com.withsports.teamservice.domain.teammember.service;

import com.withsports.teamservice.domain.team.entity.Team;
import com.withsports.teamservice.domain.team.exception.NotExistTeamException;
import com.withsports.teamservice.domain.team.repository.TeamRepository;
import com.withsports.teamservice.domain.teammember.entity.TeamMember;
import com.withsports.teamservice.domain.teammember.repository.TeamMemberRepository;
import com.withsports.teamservice.global.entity.Role;
import com.withsports.teamservice.global.entity.Sports;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService {

    private final TeamMemberRepository teamMemberRepository;
    private final TeamRepository teamRepository;


    @Override
    @Transactional
    public void JoinTeamMember(Long teamId,
                               Long userId,
                               String sports) {

        Team team = teamRepository.finByTeamId(teamId)
                .orElseThrow(() -> new NotExistTeamException("해당 팀이 없습니다. id=" + teamId));

        TeamMember teamMember = TeamMember.of(userId, Role.NORMAL, sports ,team);

        teamMemberRepository.save(teamMember);
    }
}
