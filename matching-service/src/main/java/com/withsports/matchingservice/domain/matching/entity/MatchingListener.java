package com.withsports.matchingservice.domain.matching.entity;


import com.withsports.matchingservice.domain.matching.exception.MatchingListenerException;
import com.withsports.matchingservice.domain.matching.service.MatchingProducer;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MatchingListener {

    @Lazy
    @Autowired
    private MatchingProducer matchingProducer;

    @PostUpdate
    public void postUpdate(Matching matching){
        MatchingStatus matchingStatus = matching.getMatchingStatus();
        log.info("[MatchingListener] {}", MatchingStatus.BEFORE_BOOKING.name());
        if (matchingStatus == MatchingStatus.BEFORE_BOOKING) {
            try{
                matchingProducer.beforeBooking(matching);
            }catch (Exception ex){
                throw new MatchingListenerException(ex.getMessage());
            }
//
//        } else if (matchingStatus == MatchingStatus.AFTER_BOOKING) {
//            try {
//                matchingProducer.notifyByAfterBooking(matching);
//            } catch (Exception ex) {
//                throw new MatchingListenerException(ex.getMessage());
//            }
//        }
//        else if (matchingStatus == MatchingStatus.) {
//            try {
//                orderProducer.notifyOrderRejected(order, );
//            } catch (Exception ex) {
//                throw new OrderException(ex.getMessage());
//            }
        }
    }


}
