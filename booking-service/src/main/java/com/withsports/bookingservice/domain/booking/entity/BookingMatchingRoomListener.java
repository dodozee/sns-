package com.withsports.bookingservice.domain.booking.entity;


import com.withsports.bookingservice.domain.booking.exception.BookingMatchingException;
import com.withsports.bookingservice.domain.booking.service.BookingProducer;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookingMatchingRoomListener {

    @Lazy
    @Autowired
    private BookingProducer bookingProducer;

    @PostUpdate
    public void postUpdate(BookingMatchingRoom bookingMatchingRoom){
        BookingStatus bookingStatus = bookingMatchingRoom.getStatus();
        log.info("[OrderListener] {}", BookingStatus.COMPLETED.name());
        if (bookingStatus == BookingStatus.COMPLETED) {
            try{
                bookingProducer.bookingCompleted(bookingMatchingRoom, bookingMatchingRoom.getBooking().getYear()+"년 " +bookingMatchingRoom.getBooking().getMonth()+"월 "+bookingMatchingRoom.getBooking().getDay()+"일 "+bookingMatchingRoom.getBooking().getHour()+"시 예약이 완료되었습니다.");
            }catch (Exception ex){
                throw new BookingMatchingException(ex.getMessage());
            }

        }
    }


}
