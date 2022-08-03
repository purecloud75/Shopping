package com.shop.entity;
// 주문 엔티티

import com.shop.constant.OrderStatus;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne  // 1명의 회원은 여러 번 주문할 수 있으므로 주문 엔티티 기준에서 다대일  단방향 매핑.
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;  // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;
}
