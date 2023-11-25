package com.withsports.gifticonservice.domain.order.service;

import com.withsports.gifticonservice.domain.order.dto.ReceivedGifticonDto;
import com.withsports.gifticonservice.domain.order.dto.producer.KafkaProduceOrderAcceptedDto;
import com.withsports.gifticonservice.domain.order.dto.producer.KafkaProduceOrderRejectedDto;
import com.withsports.gifticonservice.domain.order.dto.OrderGifticonDto;
import com.withsports.gifticonservice.domain.order.dto.SentGifticonDto;
import com.withsports.gifticonservice.domain.order.dto.producer.KafkaProduceUpdateUserProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    void orderGifticon(OrderGifticonDto orderGifticonDto) throws Exception;

    void orderAccepted(KafkaProduceOrderAcceptedDto kafkaProduceOrderAcceptedDto) throws InterruptedException;

    void orderRejected(KafkaProduceOrderRejectedDto kafkaProduceOrderAcceptedDto) throws Exception;

    Page<ReceivedGifticonDto> getReceivedGifticonList(Pageable pageable, Long toUserId);

    Page<SentGifticonDto> getSentGifticonList(Pageable pageable,Long fromUserId);

    void updatedUserForGifticon(KafkaProduceUpdateUserProfileDto kafkaProduceUpdateUserProfileDto);
}
