package com.withsports.bookingservice.domain.stadium.entity;


import com.withsports.bookingservice.domain.booking.dto.RegisterStadiumDateDto;
import com.withsports.bookingservice.domain.stadium.web.request.RegisterStadiumRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "stadium")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stadium {
    @Id @GeneratedValue
    @Column(name = "stadium_id")
    private Long id;
    @Column(name = "stadium_name")
    private String stadiumName;
    private String address;
    private String detailAddress;
    private String phoneNumber;
    private Long adminId;

    @OneToMany(mappedBy = "stadium", cascade = CascadeType.ALL)
    private List<AvailableDate> availableDates = new ArrayList<>();

    public static Stadium createStadium(RegisterStadiumRequest registerStadiumRequest, String adminId) {
        Stadium stadium = new Stadium();
        stadium.stadiumName = registerStadiumRequest.getStadiumName();
        stadium.address = registerStadiumRequest.getAddress();
        stadium.detailAddress = registerStadiumRequest.getDetailAddress();
        stadium.phoneNumber = registerStadiumRequest.getPhoneNumber();
        stadium.adminId = Long.parseLong(adminId);
        return stadium;
    }

//    @Column(name = "total_quantity")
//    private Integer totalQuantity;
//
//    @Column(name = "date_accept")
//    private Integer dateAccept;
//
//    @Column(name = "time_accept")
//    private Integer timeAccept;


    public void addAvailableDate(AvailableDate availableDate){
        availableDates.add(availableDate);
        availableDate.setStadium(this);
    }

    public AvailableDate registerStadiumDate(RegisterStadiumDateDto registerStadiumDateDto) {
        AvailableDate availableDate = AvailableDate.createAvailableDate(registerStadiumDateDto);
        addAvailableDate(availableDate);
        return availableDate;
    }
}
