package com.shop.entity;
// 주문 상품 엔티티

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne (fetch = FetchType.LAZY)  // 지연 로딩 방식. 즉시 로딩은 작성하고 있는 비즈니스 로직에서 안 사용하는 데이터도 한꺼번에 들고오는 것이다.
    // 하나의 상품은 여러 주문 상품으로 들어갈 수 있으므로 주문 상품 엔티티를 기준으로 다대일 단방향 매핑.
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne (fetch = FetchType.LAZY)
    // 한 번의 주문에 여러 개의 상품을 주문할 수 있으므로 주문 상품 엔티티 기준으로 다대일 단방향 매핑.
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

}
