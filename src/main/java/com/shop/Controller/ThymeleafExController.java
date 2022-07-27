package com.shop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
