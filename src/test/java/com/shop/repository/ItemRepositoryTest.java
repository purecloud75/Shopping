package com.shop.repository;
// ItemRepository를 테스트하는 코드를 작성하는 곳임.
// hibernate_sequence라는 키 생성 전용테이블로부터 다음에 저장할 상품의 기본키를 가져와서 item 테이블의 기본키 값으로 넣어줌.
// ItemRepository 인터페이스를 작성한 것만으로 상품 테이블에 데이터를 insert 할 수 있었음. 따로 Dao와 xml파일에 쿼리문을 작성할 필요가 없음. spring data JPA 덕분에.

import static org.junit.jupiter.api.Assertions.*;

import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.shop.entity.QItem;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;


@SpringBootTest  // 실제 어플을 구동할 때처럼 모든 Bean을 IoC컨테이너에 등록함.
@TestPropertySource(locations = "classpath:application-test.properties")  // ..tion.pro.. 보다 ..tion-test.pro.. 을 더 우선적으로 적용함. 즉 h2를 이용하게됨.
class ItemRepositoryTest {

    @PersistenceContext  // 영속성 컨텍스트를 사용하기 위해 해당 어노테이션을 이용해 엔티티매니저 빈을 주입함.
    EntityManager em;

    @Autowired  // ItemRepository를 사용하기 위해서 이 어노테이션을 써서 Bean을 주입함.
    ItemRepository itemRepository;

    @Test  // 해당 메소드를 테스트 대상으로 지정함.
    @DisplayName("상품 저장 테스트")  // 테스트 코드 실행 시, 이 어노테이션에 지정한 테스트명이 노출됨.
    public void createItemTest() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList() {  // 테스트 코드 실행 시, h2에 상품 데이터가 없으므로 테스트 데이터 생성을 위해 10개의 상품을 저장하는 메소드를 작성하기 위함.
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());

            Item savedItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for (Item item : itemList) {
            System.out.println(item.toString());  // 조회 결과 얻은 item 객체들을 출력함.
        }
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 테스트")
    public void findByItemNmOrItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
        // 해당 상품을 itemList에 할당함. 테스트 코드를 실행하면 조건대로 2개의 상품이 출력되는 것을 볼 수 있음.
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThan() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
    // 현재 h2에 저장된 가격은 10000~10010으로, 테스트 코드 실행 시 10개의 상품을 저장하는 로그가 콘솔에 나타나고 맨 마지막에 가격이 10005보다 작은
    // 4개의 상품을 출력해주는 것을 확인할 수 있음.

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDescTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
    // 위와 똑같은 4개의 상품(10000~10004)가 검색되지만, 내림차순으로 출력됨을 알 수 있음. 단순히 메소드의 이름을 문법에 맞게만 바꾼건데, 이름대로 기능을 하네. 놀라움.

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
    // 테스트 코드 실행 결과 상품 상세 설명에 '테스트 상품 상세 설명'을 포함하고 있는 상품 데이터 10개가 가격이 높은 순부터 조회되는 것을 확인할 수 있음

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailByNativeTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
    // 기존의 데이터베이스에서 사용하던 쿼리를 그대로 쓰고싶다면 이렇게 쓰자. 다만 특정 DB에 종속되는 쿼리문을 사용하기에 독립적이지 못하다.

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void queryDslTest() {
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);  // JPAQueryFactory를 이용하여 쿼리를 동적으로 생성함.
        QItem qItem = QItem.item;  // querydsl을 통해 쿼리를 생성하기 위해 Qitem객체를 이용함.
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)  // 자바 소스코드지만 SQL문과 비슷하게 소스 작성 가능.
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();  // JPAQuery 메소드 중 하나로, 쿼리 결과를 리스트로 반환함. fetch() 실행 시점에 쿼리문이 실행됨.

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
    // @Query 괄호 안에 JPQL문법으로 문자열을 입력하기에 잘못 입력시 런타임에러. 그래서 나왔다
    // querydsl은 JPQL을 자바 소스코드로 작성 할 수 있도록 도와주는 빌더 API이다. 문자열이 아닌 자바 소스코드로 작성하기때문에 컴파일 시점에 오류 발견. IDE 자동완성기능이용 
    // 이렇게 자바 코드를 이용하여 고정된 쿼리문이 아닌 비즈니스 로직에 따라서 동적으로 쿼리문을 생성해줄 수 있음.
}