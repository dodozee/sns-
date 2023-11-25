package com.withsports.recordservice.domain.record.team.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamRecord is a Querydsl query type for TeamRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamRecord extends EntityPathBase<TeamRecord> {

    private static final long serialVersionUID = 116477153L;

    public static final QTeamRecord teamRecord = new QTeamRecord("teamRecord");

    public final NumberPath<Long> draw = createNumber("draw", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> lose = createNumber("lose", Long.class);

    public final ListPath<com.withsports.recordservice.domain.record.matching.entity.MatchingRecord, com.withsports.recordservice.domain.record.matching.entity.QMatchingRecord> matchingRecords = this.<com.withsports.recordservice.domain.record.matching.entity.MatchingRecord, com.withsports.recordservice.domain.record.matching.entity.QMatchingRecord>createList("matchingRecords", com.withsports.recordservice.domain.record.matching.entity.MatchingRecord.class, com.withsports.recordservice.domain.record.matching.entity.QMatchingRecord.class, PathInits.DIRECT2);

    public final StringPath sports = createString("sports");

    public final NumberPath<Long> teamId = createNumber("teamId", Long.class);

    public final StringPath teamName = createString("teamName");

    public final NumberPath<Long> win = createNumber("win", Long.class);

    public QTeamRecord(String variable) {
        super(TeamRecord.class, forVariable(variable));
    }

    public QTeamRecord(Path<? extends TeamRecord> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeamRecord(PathMetadata metadata) {
        super(TeamRecord.class, metadata);
    }

}

