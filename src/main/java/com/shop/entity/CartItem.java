package com.shop.entity;
// 장바구니 상품 엔티티

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 하나의 장바구니에는 여러 개의 상품(우리는 이것을 장바구니 상품이라 부른다)을 담을  수 있으므로 다대일 단방향 매핑.
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)  // 장바구니에 담을 상품의 정보를 알아야 하므로 상품 엔티티를 매핑.
    // 하나의 상품은 여러 장바구니의 장바구니 상품으로 담길 수 있으므로 다대일 단방향 매핑.
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

}
