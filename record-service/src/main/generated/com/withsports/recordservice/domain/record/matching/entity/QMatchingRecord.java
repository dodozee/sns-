package com.withsports.recordservice.domain.record.matching.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchingRecord is a Querydsl query type for MatchingRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchingRecord extends EntityPathBase<MatchingRecord> {

    private static final long serialVersionUID = -2137990623L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchingRecord matchingRecord = new QMatchingRecord("matchingRecord");

    public final com.withsports.recordservice.global.entity.QBaseTimeEntity _super = new com.withsports.recordservice.global.entity.QBaseTimeEntity(this);

    public final StringPath area = createString("area");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> drawTeamId1 = createNumber("drawTeamId1", Long.class);

    public final NumberPath<Long> drawTeamId2 = createNumber("drawTeamId2", Long.class);

    public final StringPath drawTeamName1 = createString("drawTeamName1");

    public final StringPath drawTeamName2 = createString("drawTeamName2");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> loseScore = createNumber("loseScore", Long.class);

    public final NumberPath<Long> loseTeamId = createNumber("loseTeamId", Long.class);

    public final StringPath loseTeamName = createString("loseTeamName");

    public final NumberPath<Long> matchingRoomId = createNumber("matchingRoomId", Long.class);

    public final StringPath result = createString("result");

    public final StringPath sports = createString("sports");

    public final com.withsports.recordservice.domain.record.team.entity.QTeamRecord teamRecords;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final ListPath<com.withsports.recordservice.domain.record.user.entity.UserRecord, com.withsports.recordservice.domain.record.user.entity.QUserRecord> userRecords = this.<com.withsports.recordservice.domain.record.user.entity.UserRecord, com.withsports.recordservice.domain.record.user.entity.QUserRecord>createList("userRecords", com.withsports.recordservice.domain.record.user.entity.UserRecord.class, com.withsports.recordservice.domain.record.user.entity.QUserRecord.class, PathInits.DIRECT2);

    public final NumberPath<Long> winScore = createNumber("winScore", Long.class);

    public final NumberPath<Long> winTeamId = createNumber("winTeamId", Long.class);

    public final StringPath winTeamName = createString("winTeamName");

    public QMatchingRecord(String variable) {
        this(MatchingRecord.class, forVariable(variable), INITS);
    }

    public QMatchingRecord(Path<? extends MatchingRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchingRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchingRecord(PathMetadata metadata, PathInits inits) {
        this(MatchingRecord.class, metadata, inits);
    }

    public QMatchingRecord(Class<? extends MatchingRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.teamRecords = inits.isInitialized("teamRecords") ? new com.withsports.recordservice.domain.record.team.entity.QTeamRecord(forProperty("teamRecords")) : null;
    }

}

