package com.shop.service;

import static org.junit.jupiter.api.Assertions.*;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional  // 테스트 클래스에 해당 어노테이션을 선언하면, 테스트 실행 후 롤백처리가 되어서 같은 메소드를 반복적으로 테스트할 수 잇음
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 회원정보를 입력한 Member 엔티티를 만드는 메소드이다. 원래는 회원가입 페이지에서 받은 정보로 Dto를 구성해야 하지만, 일단은 여기서 임의로 정보을 넣어주었다.
    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();

        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");

        return Member.createMember(memberFormDto, passwordEncoder);
        // 해당 메소드는 Member 엔티티 클래스에 정의된 static 메소드여서, 바로 클래스명.메소드명으로 호출이 됨.
        // 호출이 되면서 Dto의 값들이 Entity로 옮겨지고, 그 값으로 채워진 엔티티 클래스의 인스턴스를 반환하면서 내부 메소드 종료, 반환된 인스턴스를 반환하면서 외부 메소드도 종료.
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {  // 저장하려고 요청했던 값과 실제 저장된 데이터를 비교. 첫번째는 예상 값, 두번째는 실제로 저장된 값을 넣어준다.
        Member member = createMember();  // 이 메소드가 호출된 시점에서 DB내 테이블과 매핑되어있는 엔티티에 회원정보가 잘 저장이 되어있다.
        Member savedMember = memberService.saveMember(member);  // 이메일로 중복가입여부를 체크한 후 엔티티를 DB로 보내서 저장하였다. 그 엔티티를 반환.

        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getRole(), savedMember.getRole());
        // 이 메소드들은 결국 DB로 보내지기 전의 엔티티와 DB로 보내서 저장까지 완료된 엔티티의 필드값을 비교하는 것으로, 당연히 같은 엔티티여서 똑같다.
    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() {
        Member member1 = createMember();  // 이메일은 test@email.com
        Member member2 = createMember();  // 이메일은 test@email.com 로, 둘은 똑같다.
        memberService.saveMember(member1);  // 멤버1의 데이터만 엔티티를  통해 DB에 저장하였다.

        //
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);
        });
        assertEquals("이미 가입된 회원입니다.", e.getMessage());
        //멤버2의 데이터도 DB에 저장하는 과정에서 중복체크를 수행하는데, IllegalStateException 예외객체가 생성되었을 것이고, 그 예외객체가 e에 저장되었다.
        // MemberService클래스에 정의된 중복체크메소드 중 throw new IllegalStateException("이미 가입된 회원입니다.")부분에서 요 괄호안이 e.getMessage()이다.
        // 그러니까 중복이라면 해당 예외클래스의 객체가 생성되었고, 그 객체의 getMessage()에는 이미가입 문장이 저장되어있으니, 첫번째 인자인 예상 값과 비교한다.
        // 그래서 ture가 반환된다면 중복이 있다고 최종 확인을 한다.

    }
}