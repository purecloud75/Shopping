package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.shop.dto.ItemFormDto;

@Controller
public class ItemController {

    @GetMapping(value = "/admin/item/new")
    public String itemForm(Model model) {
        model.addAttribute("itemFormDto", new ItemController());
        return "item/itemForm";  // value에 매핑된 url로 접속하면, 해당 메소드가 실행되어 상품 등록 페이지(관리자용 페이지)를 띄운다.
    }
}
