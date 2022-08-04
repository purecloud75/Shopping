package com.shop.entity;
// 주문 엔티티

import com.shop.constant.OrderStatus;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 1명의 회원은 여러 번 주문할 수 있으므로 주문 엔티티 기준에서 다대일  단방향 매핑.
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;  // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
    , orphanRemoval = true,fetch = FetchType.LAZY)
    // 주문 상품 엔티티와 일대다 매핑. 이로써 양방향 매핑이 됨. 연관관계설정. 주인이 아닌쪽은 mappedBy
    // 부모 엔티티의 영속성 상태 변화를 자식 엔티티에 모두 전이하도록 설정.
    private List<OrderItem> orderItems = new ArrayList<>();  // 하나의 주문이 여러 개의 주문 상품을 가지기에 List 자료형을 사용해서 매핑.

}
