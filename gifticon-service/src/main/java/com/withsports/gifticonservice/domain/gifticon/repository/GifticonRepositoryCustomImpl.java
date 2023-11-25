package com.withsports.gifticonservice.domain.gifticon.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.withsports.gifticonservice.domain.gifticon.entity.Gifticon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.withsports.gifticonservice.domain.gifticon.entity.QGifticon.gifticon;


@Slf4j
@Repository
@RequiredArgsConstructor
public class GifticonRepositoryCustomImpl implements GifticonRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public Page<Gifticon> getGifticonList(Pageable pageable, String categoryName){

        Long count = queryFactory.select(gifticon.count())
                .from(gifticon)
                .where(gifticon.categoryName.eq(categoryName))
                .fetchOne();

        List<Gifticon> gifticons = queryFactory
                .selectFrom(gifticon)
                .where(gifticon.categoryName.eq(categoryName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();



        return PageableExecutionUtils.getPage(gifticons, pageable, () -> count);
    }
}
