package com.withsports.recordservice.domain.record.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.withsports.recordservice.domain.record.matching.entity.MatchingRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.withsports.recordservice.domain.record.matching.entity.QMatchingRecord.matchingRecord;
import static com.withsports.recordservice.domain.record.user.entity.QUserRecord.userRecord;

@Repository
@RequiredArgsConstructor
public class UserRecordRepositoryCustomImpl implements UserRecordRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    public Page<MatchingRecord> getUserRecordHistoryByUserId(Long userId, Pageable pageable) {

            Long count = queryFactory.select(matchingRecord.count())
                    .from(matchingRecord)
                    .join(matchingRecord.userRecords, userRecord)
                    .where(userRecord.userId.eq(userId))
                    .fetchOne();

            List<MatchingRecord> matchingRecords = queryFactory.select(matchingRecord)
                    .from(matchingRecord)
                    .join(matchingRecord.userRecords, userRecord)
                    .where(userRecord.userId.eq(userId))
                    .fetch();

            return PageableExecutionUtils.getPage(matchingRecords, pageable, () -> count);
        }

}
