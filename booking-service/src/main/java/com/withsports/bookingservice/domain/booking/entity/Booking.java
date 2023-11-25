package com.withsports.bookingservice.domain.booking.entity;

import com.withsports.bookingservice.domain.booking.dto.DateAndTimeDto;
import com.withsports.bookingservice.domain.stadium.entity.Stadium;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "booking")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Booking {

    @Id @GeneratedValue
    @Column(name = "booking_id")
    private Long id;
    @Column(name = "matching_room_id")
    private Long matchingRoomId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer capacity;
    @OneToOne(mappedBy = "booking", fetch = FetchType.LAZY)
    private BookingMatchingRoom bookingMatchingRoom;

    public static Booking createBooking(DateAndTimeDto dateAndTimedto) {
        Booking booking = new Booking();
        booking.year = dateAndTimedto.getYear();
        booking.month = dateAndTimedto.getMonth();
        booking.day = dateAndTimedto.getDay();
        booking.hour = dateAndTimedto.getHour();
        booking.capacity = dateAndTimedto.getCapacity();
        return booking;
    }


    public void setBookingMatchingRoom(BookingMatchingRoom bookingMatchingRoom){
        this.bookingMatchingRoom = bookingMatchingRoom;
    }

}
