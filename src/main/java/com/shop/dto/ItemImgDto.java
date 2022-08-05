package com.shop.dto;
// 상품 저장 후 상품 이미지에 대한 데이터를 전달할 DTO 클래스

import com.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repimgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg) {
        return modelMapper.map(itemImg,ItemImgDto.class);
    }
    // ItemImg 엔티티 객체를 파라미터로 받아서, ItemImg 객체의 자료형과 멤버변수의 이름이 같을 때,
    // ItemImgDto로 값을 복사해서 반환함. static 메소드로 선언해 ItemImgDto 객체를 생성하지 않아도 호출할 수 있도록 함.

    // 이 메소드는 상품정보를 DB로부터 조회할 때, 즉 엔티티 -> DTO로 이어지는 데이터의 흐름에 기여하는 친구이다.
    // 반대로 엔티티 클래스에는 DTO를 파라미터로 받아서, 시그니처가 같으면 DTO의 값을 엔티티로 복사한다.

}