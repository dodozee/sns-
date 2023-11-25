package com.withsport.userservice.domain.admin.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.withsport.userservice.domain.user.entity.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.withsport.userservice.domain.user.entity.QReport.report;


@Repository
@RequiredArgsConstructor
public class ReportRepositoryCustomImpl implements ReportRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public Page<Report> getReportHistory(Pageable pageable) {

        Long count = queryFactory.select(report.count())
                .from(report)
                .fetchOne();


        List<Report> reports = queryFactory
                .selectFrom(report)
                .orderBy(report.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return PageableExecutionUtils.getPage(reports, pageable, () -> count);
    }
}


