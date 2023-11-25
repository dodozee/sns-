package com.withsports.recordservice.domain.record.user.repository;

import com.withsports.recordservice.domain.record.matching.entity.MatchingRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRecordRepositoryCustom {

    Page<MatchingRecord> getUserRecordHistoryByUserId(Long userId, Pageable pageable);

}
