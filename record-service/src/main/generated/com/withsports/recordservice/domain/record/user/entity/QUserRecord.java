package com.withsports.recordservice.domain.record.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRecord is a Querydsl query type for UserRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserRecord extends EntityPathBase<UserRecord> {

    private static final long serialVersionUID = -1732569667L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRecord userRecord = new QUserRecord("userRecord");

    public final NumberPath<Long> draw = createNumber("draw", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> lose = createNumber("lose", Long.class);

    public final com.withsports.recordservice.domain.record.matching.entity.QMatchingRecord matchingRecord;

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Long> rating = createNumber("rating", Long.class);

    public final StringPath sports = createString("sports");

    public final EnumPath<Tier> tier = createEnum("tier", Tier.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final NumberPath<Long> win = createNumber("win", Long.class);

    public QUserRecord(String variable) {
        this(UserRecord.class, forVariable(variable), INITS);
    }

    public QUserRecord(Path<? extends UserRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRecord(PathMetadata metadata, PathInits inits) {
        this(UserRecord.class, metadata, inits);
    }

    public QUserRecord(Class<? extends UserRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.matchingRecord = inits.isInitialized("matchingRecord") ? new com.withsports.recordservice.domain.record.matching.entity.QMatchingRecord(forProperty("matchingRecord"), inits.get("matchingRecord")) : null;
    }

}

