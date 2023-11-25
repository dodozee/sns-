package com.withsports.matchingservice.domain.matching.repository;

import com.withsports.matchingservice.domain.matching.redis.Matching;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
public interface MatchingRedisRepository extends CrudRepository<Matching, String> {
    List<Matching> findAllBySports(String sports);
}

