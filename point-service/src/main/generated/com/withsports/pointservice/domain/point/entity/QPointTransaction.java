package com.withsports.pointservice.domain.point.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPointTransaction is a Querydsl query type for PointTransaction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPointTransaction extends EntityPathBase<PointTransaction> {

    private static final long serialVersionUID = -1918765954L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPointTransaction pointTransaction = new QPointTransaction("pointTransaction");

    public final com.withsports.pointservice.global.entity.QBaseTimeEntity _super = new com.withsports.pointservice.global.entity.QBaseTimeEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final NumberPath<Long> balanceAtThatTime = createNumber("balanceAtThatTime", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPoint point;

    public final DateTimePath<java.time.LocalDateTime> transactionTime = createDateTime("transactionTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QPointTransaction(String variable) {
        this(PointTransaction.class, forVariable(variable), INITS);
    }

    public QPointTransaction(Path<? extends PointTransaction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPointTransaction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPointTransaction(PathMetadata metadata, PathInits inits) {
        this(PointTransaction.class, metadata, inits);
    }

    public QPointTransaction(Class<? extends PointTransaction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.point = inits.isInitialized("point") ? new QPoint(forProperty("point")) : null;
    }

}

