package com.withsports.recordservice.domain.record.user.repository;

import com.withsports.recordservice.domain.record.user.entity.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRecordRepository extends JpaRepository<UserRecord, Long> {
    @Query("select u from UserRecord u where u.userId = :userId and u.sports = :sports")
    UserRecord findByUserIdAndSports(@Param("userId") Long userId, @Param("sports") String sports);


    @Query("select u from UserRecord  u where u.userId =:userid")
    List<UserRecord> findByUserId(@Param("userid") Long userId);
}
