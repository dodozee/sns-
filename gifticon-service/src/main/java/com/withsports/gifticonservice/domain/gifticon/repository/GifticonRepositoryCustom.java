package com.withsports.gifticonservice.domain.gifticon.repository;

import com.withsports.gifticonservice.domain.gifticon.entity.Gifticon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GifticonRepositoryCustom {

    Page<Gifticon> getGifticonList(Pageable pageable, String categoryName);
}
