package com.withsports.bookingservice.domain.stadium.repository;

import com.withsports.bookingservice.domain.stadium.entity.AvailableTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long> {

    @Query("select a from AvailableTime a " +
            "join a.availableDate d " +
            "join d.stadium s " +
            "where s.id = :stadiumId and d.year = :year and d.month = :month and d.day = :day and a.hour = :hour and a.capacity >= :capacity")
    Optional<AvailableTime> findByAvailableDateAndHourAndCapacity(Long stadiumId, Integer year, Integer month, Integer day, Integer hour, Integer capacity);
}
