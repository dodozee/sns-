package com.withsport.userservice.domain.admin.repository;

import com.withsport.userservice.domain.user.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("select r from Report r where r.reportedUserId = :reportedId")
    List<Report> findAllById(@Param("reportedId") Long reportedId);
}
