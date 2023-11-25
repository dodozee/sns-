package com.withsports.recordservice.domain.record.matching.repository;

import com.withsports.recordservice.domain.record.matching.entity.MatchingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingRecordRepository extends JpaRepository<MatchingRecord, Long> {

}
