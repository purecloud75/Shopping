package com.shop.controller;
// 회원가입 후 메인 페이지로 갈 수 있도록 한다.

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = "/")
    public String main() {
        return "main";  // 메인 페이지는 추후 등록된 상품의 목록을 보여주는 곳이 될거다.
    }
}
