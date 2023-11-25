package com.withsports.bookingservice.domain.booking.web.response;


import com.withsports.bookingservice.domain.booking.dto.CompletedBookingDetailDto;
import com.withsports.bookingservice.domain.booking.entity.BookingMatchingRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompletedBookingDetailResponse {
    private Long bookingId; // 예약 id
    private Long stadiumId; // 구장 id
    private String stadiumName; // 구장 이름
    private String stadiumPhone; // 구장 전화번호
    private String stadiumAddress;//주소 + 상세주소
    private Integer year; // 연도
    private Integer month; // 1~12월 중 하나
    private Integer day; // 1~31일 중 하나
    private Integer hour; // 0~23시 중 하나
    private String sports; // 예약한 종목
    private String matchingRoomName1; // 매칭방1 이름
    private String matchingRoomName2; // 매칭방2 이름
    private Integer capacity;//매칭방1+매칭방2의 합임, 즉, 2배로 되어있음 2로 나누고 3대3 혹은 4대4 혹은 10대10 이렇게 해야함


    public static CompletedBookingDetailResponse of(CompletedBookingDetailDto completedBookingDetailDto) {
        return CompletedBookingDetailResponse.builder()
                .bookingId(completedBookingDetailDto.getBookingId())
                .stadiumId(completedBookingDetailDto.getStadiumId())
                .stadiumName(completedBookingDetailDto.getStadiumName())
                .stadiumPhone(completedBookingDetailDto.getStadiumPhone())
                .stadiumAddress(completedBookingDetailDto.getStadiumAddress())
                .year(completedBookingDetailDto.getYear())
                .month(completedBookingDetailDto.getMonth())
                .day(completedBookingDetailDto.getDay())
                .hour(completedBookingDetailDto.getHour())
                .sports(completedBookingDetailDto.getSports())
                .matchingRoomName1(completedBookingDetailDto.getMatchingRoomName1())
                .matchingRoomName2(completedBookingDetailDto.getMatchingRoomName2())
                .capacity(completedBookingDetailDto.getCapacity())
                .build();
    }
}
