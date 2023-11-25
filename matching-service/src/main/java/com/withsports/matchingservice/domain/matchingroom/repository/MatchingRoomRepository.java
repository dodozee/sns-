package com.withsports.matchingservice.domain.matchingroom.repository;

import com.withsports.matchingservice.domain.matchingroom.entity.MatchingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchingRoomRepository extends JpaRepository<MatchingRoom, Long> {
    @Query("select mr from MatchingRoom mr where mr.teamId = :teamId")
    List<MatchingRoom> findMatchingRoomByTeamId(@Param("teamId") Long teamId);


    @Query("select mr from MatchingRoom mr where mr.roomLeaderId = :roomLeaderId")
    Optional<MatchingRoom> findByRoomLeaderId(Long roomLeaderId);
}
