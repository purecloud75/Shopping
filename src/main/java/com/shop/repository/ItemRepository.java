package com.shop.repository;

import com.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;  // 해당 인터페이스가 상속받고자하는 상위 인터페이스이다.
// 즉 해당 인터페이스의 구현클래스에서는 상위와 하위인터페이스에 선언만 되어있는 모든 추상메소드(자동으로 abstract를 붙여줌)를 구현해야만 한다.
// Repository 인터페이스를 활용하면 엔티티매니저를 생성하여 영속성 컨텍스트(1차캐시, 쓰기 지연 SQL 저장소)에 잠시 저장시키다가
// flush()가 실행되면서 1차캐시의 엔티티 데이터와 SQL 저장소의 SQL문이 DB로 전송되면서 저장시키는 일련의 과정을 대신 해준다!!
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> { // 엔티티 타입 클래스, 기본키의 타입

    List<Item> findByItemNm(String itemNm);  // 상품의 이름을 이용하여 데이터를 조회하려함. 쿼리 메소드를 이용하는 것이다. find+By+변수이름  형태이다.
    // 매개변수로는 검색할 때 사용할 상품명 변수를 넘겨준다.

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    // 한 두개 이상의 조건을 이용해서 상품 데이터 조회 시, 쿼리메소드가 많이 길어져 해석에 불리. 이 때 스프링 데이터 JPA에서 제공하는 @Query를 이용하면
    // SQL과 유사한 JPQL이라는 객체지향 쿼리언어를 통해 복잡한 쿼리를 처리함. SQL은 DB의 테이블을 대상으로, JPQL은 엔티티 객체를 대상으로 쿼리를 수행함.
    // JPQL은 특정 DB에 의존하기 않기때문에, JPQL로 작성했다면 DB가 변경되어도 어플 실행에 영향을 받지 않음
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc ")  // 괄호 안에 JPQL로 작성한 쿼리문을 넣어줌.
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
    // @Param을 이용하여 매개변수로 넘어온 값을 JPQL에 들어갈 변수로 지정해줌.
    // itemDetail 변수를 'like % %' 사이에 ':itemDetail'로 값이 들어가도록 작성함.

    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc ",nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
    // 기존의 데이터베이스에서 사용하던 쿼리를 그대로 쓰고싶다면 이렇게 쓰자. 다만 특정 DB에 종속되는 쿼리문을 사용하기에 독립적이지 못하다.
}
