package com.withsports.recordservice.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QImage is a Querydsl query type for Image
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QImage extends BeanPath<Image> {

    private static final long serialVersionUID = 1344293781L;

    public static final QImage image = new QImage("image");

    public final StringPath imageName = createString("imageName");

    public final StringPath imageUrl = createString("imageUrl");

    public QImage(String variable) {
        super(Image.class, forVariable(variable));
    }

    public QImage(Path<? extends Image> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImage(PathMetadata metadata) {
        super(Image.class, metadata);
    }

}

