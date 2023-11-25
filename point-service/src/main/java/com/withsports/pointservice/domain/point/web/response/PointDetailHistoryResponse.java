package com.withsports.pointservice.domain.point.web.response;


import com.withsports.pointservice.domain.point.dto.PointDetailHistory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PointDetailHistoryResponse {

        private List<PointDetailHistory> points;
        private Page page;

    @Data @AllArgsConstructor
    static class Page {
        int startPage;
        int totalPage;
    }
    public PointDetailHistoryResponse(List<PointDetailHistory> points, int startPage, int totalPage) {
        this.points = points;
        this.page = new Page(startPage, totalPage);


    }




//    public static ReceivedGifticonResponse of(ReceivedGifticonDto dto) {
//            return ReceivedGifticonResponse.builder()
//                    .gifticonId(dto.getGifticonId())
//                    .gifticonName(dto.getGifticonName())
//                    .fromUserNickname(dto.getFromUserNickname())
//                    .toUserNickname(dto.getToUserNickname())
//                    .serialNumber(dto.getSerialNumber())
//                    .letter(dto.getLetter())
//                    .isUsed(dto.isUsed())
//                    .build();
//        }
}
