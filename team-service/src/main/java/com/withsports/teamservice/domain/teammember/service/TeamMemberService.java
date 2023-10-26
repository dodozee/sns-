package com.withsports.teamservice.domain.teammember.service;

import com.withsports.teamservice.global.entity.Sports;

public interface TeamMemberService {

    public void JoinTeamMember(Long teamId, Long userId, String sports);
}
