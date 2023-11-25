package com.withsports.gifticonservice.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -221168049L;

    public static final QOrder order = new QOrder("order1");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final NumberPath<Long> fromUserId = createNumber("fromUserId", Long.class);

    public final StringPath fromUserNickname = createString("fromUserNickname");

    public final NumberPath<Long> gifticonId = createNumber("gifticonId", Long.class);

    public final StringPath gifticonName = createString("gifticonName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isUsed = createBoolean("isUsed");

    public final StringPath letter = createString("letter");

    public final EnumPath<OrderStatus> orderStatus = createEnum("orderStatus", OrderStatus.class);

    public final DateTimePath<java.time.LocalDateTime> orderTime = createDateTime("orderTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final StringPath serialNumber = createString("serialNumber");

    public final NumberPath<Long> toUserId = createNumber("toUserId", Long.class);

    public final StringPath toUserNickname = createString("toUserNickname");

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<? extends Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}

