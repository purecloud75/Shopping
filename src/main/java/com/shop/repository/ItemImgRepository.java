package com.shop.repository;
// 상품의 이미지를 저장하기 위한 인터페이스

import com.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
    // 매개변수로 넘겨준 상품 아이디를 가지며, 상품 이미지 아이디의 오름차순으로 가져오는 쿼리 메소드이다.
}