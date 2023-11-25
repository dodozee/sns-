package com.withsports.pointservice.domain.point.repository;

import com.withsports.pointservice.domain.point.entity.PointTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointTransactionRepositoryCustom {

    Page<PointTransaction> getPointTransactionHistoryByUserId(Long userId, Pageable pageable);
}
