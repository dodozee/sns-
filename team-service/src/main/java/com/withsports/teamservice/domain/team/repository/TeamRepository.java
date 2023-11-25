package com.withsports.teamservice.domain.team.repository;

import com.withsports.teamservice.domain.team.entity.Team;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

    @EntityGraph(attributePaths = {"teamUsers"})
    List<Team> findByLeaderId(Long leaderId);

    @Query("select t from Team t where t.teamName = :teamName")
    Optional<Team> findByTeamName(@Param("teamName") String teamName);

    @Query("select t from Team t join fetch t.teamUsers where t.id=:teamId and t.leaderId=:userId")
    Optional<Team> findByIdAndUserId(@Param("teamId") Long teamId, @Param("userId") Long userId);

    List<Team> findUserByTeamNameContaining(String teamName);

    @Query("select t from Team t  where t.leaderId=:userId")
    List<Team> findTeamsByLeaderId(Long userId);

    @Query("select t.id from Team t  where t.leaderId=:leaderId and t.sports=:sports")
    Long findTeamIdByLeaderIdAndSports(Long leaderId, String sports);
}
