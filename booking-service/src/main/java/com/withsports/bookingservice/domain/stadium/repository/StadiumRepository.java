package com.withsports.bookingservice.domain.stadium.repository;

import com.withsports.bookingservice.domain.stadium.dto.AvailableDateAndTimeAndCapacityDto;
import com.withsports.bookingservice.domain.stadium.dto.BookingStadiumDateAndTimeDto;
import com.withsports.bookingservice.domain.stadium.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {

    List<Stadium> findByAddressContaining(String area);

    @Query("select new com.withsports.bookingservice.domain.stadium.dto.AvailableDateAndTimeAndCapacityDto(s.id, a.year, a.month, a.day, t.hour, t.capacity) " +
            "from Stadium s " +
            "join s.availableDates a " +
            "join a.availableTimes t " +
            "where s.id = :stadiumId")
    List<AvailableDateAndTimeAndCapacityDto> findStadiumDateAndTimeAndCapacityById(@Param("stadiumId") Long stadiumId);


    @Query("select new com.withsports.bookingservice.domain.stadium.dto.BookingStadiumDateAndTimeDto(s.id, a.year, a.month, a.day, t.hour, t.capacity) " +
            "from Stadium s " +
            "join s.availableDates a " +
            "join a.availableTimes t " +
            "where s.id = :stadiumId and a.year = :year and a.month = :month and a.day = :day and t.hour = :hour and t.capacity >= :capacity")
    Optional<BookingStadiumDateAndTimeDto> findByAvailableDateAndStadiumId(Long stadiumId, Integer year, Integer month, Integer day, Integer hour, Integer capacity);
}


