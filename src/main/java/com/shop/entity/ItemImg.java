package com.shop.entity;
// Item 클래스가 상품의 정보를 가지고 있는 상품 엔티티 라면,
// ItemImg 클래스는 상품의 이미지를 저장하는 상품 이미지 엔티티 이다.

// 상품을 등록할 때는 화면으로부터 전달받은 DTO 객체를 엔티티 객체로 변환하는 작업을 해야하고,
// 상품을 조회할 때는 엔티티 객체를 DTO 객체로 바꿔주는 작업을 해야 합니다. 이 작업은 반복적인 작업입니다.

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "item_img")
@Setter
@Getter
public class ItemImg extends BaseEntity {  // 등록 및 수정 시간을 실시간으로 반영해주는 클래스를 상속받음.

    @Id
    @Column(name = "item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;  //  대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)  // 하나의 상품에 여러 개의 상품 이미지가 매핑됨.
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
