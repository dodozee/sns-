package com.withsports.teamservice.domain.team.repository;

import com.withsports.teamservice.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
