package com.withsports.teamservice.domain.teammember.repository;

import com.withsports.teamservice.domain.team.entity.Team;
import com.withsports.teamservice.domain.teammember.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {

//    @Query("SELECT t FROM TeamMember t join fetch t.userId WHERE t.team.id = :teamId")
    List<TeamMember> findAllByUserId(Long userId);

    Long countByTeamId(Long teamId);

//    @Query("SELECT t FROM TeamMember t join fetch t.userId WHERE p.channel.id = :channelId")
//    List<TeamMember> findAllByChannelIdFetchJoin(@Param(value = "channelId") int channelId);

}
