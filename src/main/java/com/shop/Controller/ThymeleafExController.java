package com.shop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.dto.ItemDto;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf")  // 클라이언트 요청에 대해서 어떤 컨트롤러가 처리할지 매핑하는 어노테이션.
// url에 "/thymeleaf" 경로로 오는 요청을 ThymeleafExController가 처리하도록 함.
public class ThymeleafExController {

    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model) {
        model.addAttribute("data", "타임리프 예제 입니다.");  // model 객체를 이용해 뷰에 전달한 데이터를 key, value 구조로 넣어줌.
        return "thymeleafEx/thymeleafEx01";  // templates 폴더를 기준으로 뷰의 위치와 이름(thymeleafEx01.html)을 반환함.
    }
    // 디자이너는 자신이 작업한 내용을 html 파일로 바로 열어서 확인가능, 혹은 디자이너로부터 html파일을 받아서 html 태크 안에 타임리프 문법을 추가하는 것만으로
    // 동적으로 html 파일을 생성할 수 있음.

    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping(value = "/ex03")
    public String thymeleafExample03(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setItemNm("테스트 상품" + i);
            itemDto.setPrice(1000 * i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList", itemDtoList);  // 화면에서 출력할 itemDtoList를 model에 담아서 View에 전달함.
        return "thymeleafEx/thymeleafEx03";
    }
    // 여러 개의 데이터를 가지고있는 컬렉션 데이터를 화면에 출력하는 방법

    @GetMapping(value = "/ex04")
    public String thymeleafExample04(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setItemNm("테스트 상품" + i);
            itemDto.setPrice(1000 * i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }

        model.addAttribute("itemDtoList",itemDtoList);
        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping(value = "/ex05")
    public String thymeleafExample05(Model model){
        return "thymeleafEx/thymeleafEx05";
    }

    @GetMapping(value = "/ex07")
    public String thymeleafExample07(){
        return "thymeleafEx/thymeleafEx07";
    }
}
