package com.shop.constant;
// 상품이 현재 판매중인지 품절상태인지를 나타내는 enum 타입의 클래스이다.
// 이를 통해 연관된 상수들을 모아둘 수 있으며 enum 에 정의한 타입만 값을 가지도록 컴파일 시 체크할 수 있다는 장점이 있다.

public enum ItemSellStatus {
    SELL, SOLD_OUT
}
