package com.withsport.userservice.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 812147404L;

    public static final QUser user = new QUser("user");

    public final com.withsport.userservice.global.entity.QBaseTimeEntity _super = new com.withsport.userservice.global.entity.QBaseTimeEntity(this);

    public final StringPath area = createString("area");

    //inherited
    public final DatePath<java.time.LocalDate> createdAt = _super.createdAt;

    public final StringPath dtype = createString("dtype");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final EnumPath<AuthType> oauthType = createEnum("oauthType", AuthType.class);

    public final StringPath password = createString("password");

    public final StringPath profileImage = createString("profileImage");

    //inherited
    public final DatePath<java.time.LocalDate> updatedAt = _super.updatedAt;

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

