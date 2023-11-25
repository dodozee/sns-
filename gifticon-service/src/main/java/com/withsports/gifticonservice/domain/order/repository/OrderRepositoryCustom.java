package com.withsports.gifticonservice.domain.order.repository;


import com.withsports.gifticonservice.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

public interface OrderRepositoryCustom {

    Page<Order> getOrderHistoryByFromUser(Pageable pageable, Long fromUserId);

    Page<Order> getOrderHistoryByToUser(Pageable pageable, Long toUserId);



}
