package com.withsports.matchingservice.domain.matching.repository;

import com.withsports.matchingservice.domain.matching.entity.Matching;
import com.withsports.matchingservice.domain.matching.entity.MatchingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    @Query("select m from Matching m where m.matchingRoomId1 = :matchingRoomId1")
    Optional<Matching> findByMatchingRoomId1(Long matchingRoomId1);

    @Query("select m from Matching m where m.matchingRoomId2 = :matchingRoomId2")
    Optional<Matching> findByMatchingRoomId2(Long matchingRoomId2);

    @Query("select count(m) from Matching m where  m.matchingStatus = :matchingStatus")
    Long countByMatchingStatus(@Param("matchingStatus") MatchingStatus matchingStatus);

    @Query("select m from Matching m where m.matchingRoomId1 = :matchingRoomId or m.matchingRoomId2 = :matchingRoomId")
    Optional<Matching> findByMatchingRoomId1OrMatchingRoomId2(Long matchingRoomId);
}
