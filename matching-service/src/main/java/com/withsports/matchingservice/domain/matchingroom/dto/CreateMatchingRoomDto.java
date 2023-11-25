package com.withsports.matchingservice.domain.matchingroom.dto;

import com.withsports.matchingservice.domain.matchingroom.web.request.CreateMatchingRoomRequest;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMatchingRoomDto {
    private String title;
    private String sports;
    private String Area;
    private Long capacity;


    public static CreateMatchingRoomDto of(CreateMatchingRoomRequest request) {
        return CreateMatchingRoomDto.builder()
                .title(request.getTitle())
                .sports(request.getSports())
                .Area(request.getArea())
                .capacity(request.getCapacity())
                .build();
    }

}
