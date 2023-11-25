package com.withsports.bookingservice.domain.booking.repository;

import com.withsports.bookingservice.domain.booking.entity.BookingMatchingRoom;
import com.withsports.bookingservice.domain.stadium.entity.AvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingMatchingRoomRepository extends JpaRepository<BookingMatchingRoom, Long> {

    @Query("select b from BookingMatchingRoom b where b.matchingRoomId1 = :matchingRoomId or b.matchingRoomId2 = :matchingRoomId")
    BookingMatchingRoom findByMatchingRoomId1(@Param("matchingRoomId") Long matchingRoomId);

    @Query("select b from BookingMatchingRoom b where b.matchingRoomLeaderId1 = :leaderId or b.matchingRoomLeaderId2 = :leaderId and b.status = 'DEFAULT'")
    List<BookingMatchingRoom> findByMatchingRoomLeaderId1OrAndMatchingRoomLeaderId2AndStatus(@Param("leaderId") Long leaderId);

    @Query("select a from AvailableTime a where a.availableDate.stadium.id = :stadiumId and a.availableDate.year = :year and a.availableDate.month = :month and a.availableDate.day = :day")
    List<AvailableTime> findByAvailableDateAndStadiumId(Long stadiumId, Integer year, Integer month, Integer day);

    @Query("select b from BookingMatchingRoom b where b.matchingRoomId1 = :matchingRoomId or b.matchingRoomId2 = :matchingRoomId and b.status = 'DEFAULT'")
    Optional<BookingMatchingRoom> findByMatchingRoomId1OrMatchingRoomId2(Long matchingRoomId);

    @Query("select b from BookingMatchingRoom b where b.matchingRoomTeamId1 = :teamId or b.matchingRoomTeamId2 = :teamId")
    List<BookingMatchingRoom> findByTeamId(Long teamId);
}
