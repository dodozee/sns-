package com.withsports.bookingservice.domain.stadium.entity;

import com.withsports.bookingservice.domain.booking.dto.RegisterStadiumDateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table(name="available_date")
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class AvailableDate {

    @Id @GeneratedValue
    @Column(name = "available_data_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    private Integer year;
    private Integer month;
    private Integer day;

    @OneToMany(mappedBy = "availableDate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableTime> availableTimes = new ArrayList<>();

    public static AvailableDate createAvailableDate(RegisterStadiumDateDto registerStadiumDateDto) {
        AvailableDate availableDate = new AvailableDate();
        availableDate.year = registerStadiumDateDto.getYear();
        availableDate.month = registerStadiumDateDto.getMonth();
        availableDate.day = registerStadiumDateDto.getDay();
//        AvailableTime availableTime = AvailableTime.createAvailableTime(registerStadiumDateDto);
        return availableDate;
    }

    // 일일 수용 가능 인원
//    @Column(name = "accept_count")
//    private Integer acceptCount;


    public void setStadium(Stadium stadium){
        this.stadium =stadium;
    }

    public void addAvailableTime(AvailableTime availableTime){
        availableTimes.add(availableTime);
        availableTime.setAvailableDate(this);
    }




}
