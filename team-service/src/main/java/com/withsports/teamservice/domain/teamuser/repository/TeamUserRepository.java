package com.withsports.teamservice.domain.teamuser.repository;

import com.withsports.teamservice.domain.teamuser.entity.TeamUser;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {


    @Query("select tu from TeamUser tu where tu.userId = :userId and tu.status = 'ACCEPTED'")
    List<TeamUser> findByUserId(@Param("userId") Long userId);

    void deleteTeamUserByTeamIdAndUserId(Long teamId, Long userId);

    //팀의 명수 조회
    @Query("select count(tu) from TeamUser tu where tu.team.id = :teamId AND tu.status = 'ACCEPTED'")
    Long countByTeamId(@Param("teamId") Long teamId);

    @Query("select tu from TeamUser tu join fetch tu.team t WHERE t.id = :teamId AND tu.userId = :userId AND tu.status = 'PLACED'")
    TeamUser findTeamUserByTeamIdAndUserId(@Param("teamId") Long teamId, @Param("userId") Long userId);

    @Query("select tu from TeamUser tu where tu.team.id = :teamId and tu.status = 'ACCEPTED'")
    List<TeamUser> findByTeamId(@Param("teamId") Long teamId);

    @Query("select tu.userId from TeamUser tu where tu.team.id = :teamId and tu.status = 'ACCEPTED'")
    List<Long> findUserIdsByTeamId(@Param("teamId") Long teamId);
    @Query("select tu from TeamUser tu join fetch tu.team t WHERE t.id = :teamId AND tu.userId = :userId")
    TeamUser findTeamPlacedUserByTeamIdAndUserId(@Param("teamId") Long teamId, @Param("userId") Long userId);

    @Query("select tu from TeamUser tu join fetch tu.team t WHERE t.sports = :sports AND tu.userId = :userId and tu.status = 'ACCEPTED'")
    Optional<TeamUser>  findTeamUserByUserIdAndSports(@Param("userId") Long userId, @Param("sports") String sports);

    @Query("select tu.team.id from TeamUser tu where tu.userId = :userId and tu.status = 'ACCEPTED'")
    List<Long> findTeamIdsByUserId(@Param("userId") Long userId);

    @Query("select tu from TeamUser tu join fetch tu.team t WHERE t.id = :teamId AND tu.status = 'PLACED' ORDER BY tu.createdAt DESC")
    List<TeamUser> findApplicationUserByTeamId(@Param("teamId") Long teamId);

    void deleteByTeamId(Long teamId);

    @Query("select tu from TeamUser tu WHERE tu.userId = :userId")
    List<TeamUser> findTeamByUserId(Long userId);
}
