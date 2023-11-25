package com.withsports.matchingservice.domain.matchingroom.repository;

import com.withsports.matchingservice.domain.matchingroom.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomUserRepository extends JpaRepository<RoomUser, Long> {



    //매칭룸id로 조회
//    Optional<RoomUser> findByTitleAndTeamId(String title, Long teamId);

    Optional<RoomUser> findByUserIdAndMatchingRoomId(Long userId, Long matchingRoomId);

    @Query("select ru.userId from RoomUser ru where ru.matchingRoom.id = :matchingRoomId")
    List<Long> findAllUserIdByMatchingRoomId(@Param("matchingRoomId") Long matchingRoomId);

    @Query("select ru from RoomUser ru where ru.userId = :userId")
    List<RoomUser> findByUserId(Long userId);
}
