package com.withsport.userservice.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = -1308880907L;

    public static final QReport report = new QReport("report");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isProceeded = createBoolean("isProceeded");

    public final StringPath reason = createString("reason");

    public final NumberPath<Long> reportedUserId = createNumber("reportedUserId", Long.class);

    public final StringPath reportedUserNickname = createString("reportedUserNickname");

    public final NumberPath<Long> reporterUserId = createNumber("reporterUserId", Long.class);

    public final StringPath reporterUserNickname = createString("reporterUserNickname");

    public QReport(String variable) {
        super(Report.class, forVariable(variable));
    }

    public QReport(Path<? extends Report> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReport(PathMetadata metadata) {
        super(Report.class, metadata);
    }

}

