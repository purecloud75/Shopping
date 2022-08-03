package com.shop.entity;
// 장바구나 엔티티. 회원 1명 당 1개의 장바구니가 필요하므로 장바구니와 회원은 일대일로 매핑되어있다.
// 엄밀히 말하면 장바구니 엔티티가 회원 엔티티를 일방적으로 참조하는 일대일 단방향 매핑이다
// 이렇게 매핑을 맺어주면 장바구니 엔티티를 조회하면서 회원 엔티티의 정보도 동시에 가져올 수 있는 장점이 있다.
// 다대일과 일대다는 반대관계. 양방향 매핑은 단방향 매핑이 2개 있다고 생각하시면 된다. 양방향 매핑에서는 "연관 관계 주인"을 설정해야 함.

import lombok.Setter;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Getter
@Setter
@ToString
public class Cart {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 즉시 로딩 : 엔티티를 조회할 때, 해당 엔티티와 매핑된 엔티티도 한 번에 조회하는 것을 말함.
    @OneToOne  // 멤버 엔티티와 일대일로 매핑을 함. 괄호를 적지않으면 기본값으로 즉시 로딩으로 설정됨.
    @JoinColumn(name = "member_id")  // 매핑할 외래키를 지정함. 멤버엔티티의 기본키 역할을 하는 필드명을 외래키로 설정함.
    private Member member;
}
