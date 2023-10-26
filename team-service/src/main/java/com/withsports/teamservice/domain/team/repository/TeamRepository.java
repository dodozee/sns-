package com.withsports.teamservice.domain.team.repository;

import com.withsports.teamservice.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Optional<Team> finByTeamId(Long teamId);

    List<Team> findAllByLeaderId(Long leaderId);

    @Query("select t from Team t where t.teamName = :teamName")
    Optional<Team> findByTeamName(String teamName);
//    Long findByLeaderId(Long leaderId);
}
