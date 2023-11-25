package com.withsports.bookingservice.domain.booking.dto;


import com.withsports.bookingservice.domain.booking.entity.BookingMatchingRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletedBookingDetailDto {
    private Long bookingId;
    private Long stadiumId;
    private String stadiumName;
    private String stadiumPhone;
    private String stadiumAddress;//주소 + 상세주소
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private String sports;
    private String matchingRoomName1;
    private String matchingRoomName2;
    private Integer capacity;//2배로 되어있음 나눠야함

    public static CompletedBookingDetailDto of(BookingMatchingRoom bookingMatchingRoom) {
        return CompletedBookingDetailDto.builder()
                .bookingId(bookingMatchingRoom.getBooking().getId())
                .stadiumId(bookingMatchingRoom.getBooking().getStadium().getId())
                .stadiumName(bookingMatchingRoom.getBooking().getStadium().getStadiumName())
                .stadiumPhone(bookingMatchingRoom.getBooking().getStadium().getPhoneNumber())
                .stadiumAddress(bookingMatchingRoom.getBooking().getStadium().getAddress()+bookingMatchingRoom.getBooking().getStadium().getDetailAddress())
                .year(bookingMatchingRoom.getBooking().getYear())
                .month(bookingMatchingRoom.getBooking().getMonth())
                .day(bookingMatchingRoom.getBooking().getDay())
                .hour(bookingMatchingRoom.getBooking().getHour())
                .sports(bookingMatchingRoom.getSports())
                .matchingRoomName1(bookingMatchingRoom.getMatchingRoomName1())
                .matchingRoomName2(bookingMatchingRoom.getMatchingRoomName2())
                .capacity(bookingMatchingRoom.getBooking().getCapacity())
                .build();
    }
}
