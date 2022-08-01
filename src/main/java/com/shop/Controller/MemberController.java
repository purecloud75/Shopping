package com.shop.Controller;
// 회원가입을 위한 페이지를 만들기 그 첫단계. 멤버컨트롤러 만들기.

import com.shop.dto.MemberFormDto;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.entity.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@RequestMapping("/members")
@Controller  // 빈을 주입하는 어노테이션이 포함되어있는 어노테이션이다.
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        // 최초의 비어있는 Dto객체를 생성한 후에 뷰 템플릿에 보냄. 웹페이지에 입력한 회원정보데이터가 Dto(엄밀히는 Dto 객체의 필드)에 저장이 됨.
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String newMember(@Valid MemberFormDto memberFormDto,  // 검증하려는 객체 앞에 @Valid를 선언함.
                            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {  // 회원가입 시 정보를 주어진 조건하에서 입력하지 않으면 회원가입 페이지 다시 띄워줌. 제대로 잘 입력하라고.
            return "member/memberForm";
        }
        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            // 회원정보데이터로 초기화된 Dto객체를 해당 메소드의 인자로 보냄으로써 똑같은 데이터로 초기화된 엔티티 객체가 생성됨.

            memberService.saveMember(member);
            // 생성된 엔티티 객체를 DB에 저장시키기위한 메소드이나, 중간에 email을 통해 중복가입여부를 체크 후 문제없다면 그때야 비로소 DB에 회원정보가 저장됨.

        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());  // 중복가입 예외가 발생하면 에러메시지를 뷰로 전달함.
            return "member/memberForm";
        }
        return "redirect:/";
        // return "viewName"같은 경우는 viewName에 해당하는 view를 보여주는 것이고, return "redirect:/"같은
        // 경우는 redirect오른쪽의 주소로 URL 요청을 다시 하는 것입니다.
        // 그로 인해 주소가 바뀌고 해당 URL에 속하는 컨트롤러의 함수가 한번 더 호출되는 것입니다.
        // 여기서는 redirect오른쪽의 주소는 고작 / 뿐이다. 이는 메인컨트롤러 내의 main()와 매핑되어있는 url이므로, main()을 호출해서 return "main"에 의해
        // main.html이 화면에 보여지는 것이다. 쉽게말해 떠넘기는거네.
    }
    // 회원가입이 성공하면 메인페이지로 리다이렉트 시켜주고, 회원정보 검증 및 중복회원 가입 조건에 의해 실패한다면
    // 다시 회원가입 페이지로 돌아가 실패 이유를 화면에 출력해준다.
    // try문의 saveMember()호출결과 중복이메일이 있다면 예외발생, catch{}를 수행 후 main.html을 띄움. 없다면 바로 main.html을 띄움.

    @GetMapping(value = "/login")
    public String loginMember() {
        return "/member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }
}
