package com.shop.dto;
// th:text를 이용한 상품 데이터 출력용 Dto 클래스
// 뷰 영역에서 사용할 클래스이다. 데이터를 주고받을때는 Entity클래스 자체를 반환하면 안 되고, 데이터 전달용 객체를 생성해서 사용해야 한다.

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {
    private Long id;
    private String itemNm;
    private Integer price;
    private String itemDetail;
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

}
