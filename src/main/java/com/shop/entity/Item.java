package com.shop.entity;
//엔티티 : 데이터베이스의 테이블에 대응하는 클래스 혹은 해당 클래스의 인스턴스. @Entity가 붙은 클래스는 JPA에서 관리한다.
//상품 테이블에 어떤 데이터가 저장되어야 할지 설계해야 한다. 해당 클래스는 상품의 가장 기본적인  정보들을 담고 있고 상품데이터를 관리한다.

import com.shop.constant.ItemSellStatus;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity  // item클래스를 entity로 선언함
@Table(name = "item")  // 어떤 테이블과 매핑될지를 지정함. item테이블과 매핑되도록 name을 item으로 지정함
@Getter
@Setter
@ToString

public class Item extends BaseEntity{

    // entity로 선언한 클래스는 반드시 기본키를 가져야 함. 기본키가 되는 멤버변수에 @Id를 붙여줌.
    @Id   //얘를 통해 id 멤버변수를 상품테이블의 기본키로 설정함
    @Column(name = "item_id")  // 테이블에 매핑될 칼럼의 이름을 설정함. item클래스의 id변수와    item테이블의 item_id 칼럼이 매핑되도록 함.
    @GeneratedValue(strategy = GenerationType.AUTO)  // JPA 구현체가 자동으로 기본키 생성 전략을 결정함
    private long id;  //상품코드

    @Column(nullable = false, length = 50)  // NotNull조건을 의미함. 즉 해당 필드는 항상 값이 존재해야 함. String필드는 기본길이가 255여서 바꿔줌.
    private String itemNm;  //상품명

    @Column(name = "price",nullable = false)
    private int price;  //가격

    @Column(nullable = false)
    private int stockNumber;  //재고수량

    @Lob  // 사이즈가 큰 데이터를 외부파일로 저장하거나 DB외부에 저장하기 위한 데이터 타입임. 상품 상세 설명은 딱 봐도 데이터가 클 듯. 이럴 때 쓰는 어노테이션
    @Column(nullable = false)
    private String itemDetail;  //상품 상세 설명

    @Enumerated(EnumType.STRING)  // enum 타입을 매핑해줌.
    private ItemSellStatus itemSellStatus;  //상품 판매 상태. "판매중" 혹은 재고가없으면 '코드'


    //private LocalDateTime regTime;  상품등록시간
    //private LocalDateTime updateTime;  상품수정시간
}

/*
엔티티매니저를 통해 엔티티를 영속성 컨텍스트로 보내서 저장시킨다. 영속성 컨텍스트는 애플리케이션과 데이터베이스 사이의 중간계층이다.

영컨으로 전송된 엔티티는 영컨 내부의 1차 캐시라는 곳에 저장되는데, 고유의 id를 가지며 저장된다. 그리고 이와 동시에 쓰기 지연 SQL 저장소에 SQL문이 저장된다.
트랜잭션을 커밋하는 순간 저장된 SQL문이 플러쉬되면서 데이터베이스에 엔티티가 갖고있던 데이터가 반영이 된다. 버퍼 역할을 하네. 모아서 한번에 보내니까.
*/
