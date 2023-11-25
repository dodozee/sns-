package com.withsports.matchingservice.domain.matchingroom.web.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMatchingRoomRequest {
    private String title; // 방 제목
    private String sports; // 어떤 종목인지
    private String area; // 어느 지역인지
    private Long capacity; //몇 명의 방인지
}
