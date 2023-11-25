package com.withsports.gifticonservice.domain.gifticon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGifticon is a Querydsl query type for Gifticon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGifticon extends EntityPathBase<Gifticon> {

    private static final long serialVersionUID = -1466115461L;

    public static final QGifticon gifticon = new QGifticon("gifticon");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final StringPath categoryName = createString("categoryName");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath image = createString("image");

    public final StringPath name = createString("name");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public QGifticon(String variable) {
        super(Gifticon.class, forVariable(variable));
    }

    public QGifticon(Path<? extends Gifticon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGifticon(PathMetadata metadata) {
        super(Gifticon.class, metadata);
    }

}

