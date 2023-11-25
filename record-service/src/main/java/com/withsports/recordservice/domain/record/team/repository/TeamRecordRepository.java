package com.withsports.recordservice.domain.record.team.repository;

import com.withsports.recordservice.domain.record.team.entity.TeamRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamRecordRepository extends JpaRepository<TeamRecord, Long> {


    @Query("select tr from TeamRecord tr where tr.teamId = :teamId")
    TeamRecord findRecordByTeamId(@Param("teamId") Long teamId);
}
