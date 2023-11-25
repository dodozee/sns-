package com.withsports.gifticonservice.domain.order.web.response;


import com.withsports.gifticonservice.domain.order.dto.ReceivedGifticon;
import com.withsports.gifticonservice.domain.order.dto.SentGifticon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReceivedGifticonHistoryResponse {

        private List<ReceivedGifticon> orders;
        private Page page;

    @Data @AllArgsConstructor
    static class Page {
        int startPage;
        int totalPage;
    }
    public ReceivedGifticonHistoryResponse(List<ReceivedGifticon> orders, int startPage, int totalPage) {
        this.orders = orders;
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
