package com.withsports.bookingservice.domain.stadium.entity;

import com.withsports.bookingservice.domain.booking.dto.RegisterStadiumDateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "available_time")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AvailableTime {


    @Id @GeneratedValue
    @Column(name = "available_time_id")
    private Long id;

    private Integer hour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "available_date_id")
    private AvailableDate availableDate;

    private Integer bookingStatus; //0 예약없음 : 가능 /  1 : 예약있음 : 불가능

    private Integer capacity;
    public static AvailableTime createAvailableTime(RegisterStadiumDateDto registerStadiumDateDto) {
        AvailableTime availableTime = new AvailableTime();
        availableTime.hour = registerStadiumDateDto.getHour();
        availableTime.capacity = registerStadiumDateDto.getCapacity();
        availableTime.bookingStatus = 0;
        return availableTime;
    }

    public void setAvailableDate(AvailableDate availableDate){
        this.availableDate = availableDate;
    }

    public void changeBookingStatus() {
        this.bookingStatus = 1;
    }
}
