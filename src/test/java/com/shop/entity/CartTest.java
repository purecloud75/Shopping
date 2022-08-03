package com.shop.entity;

import com.shop.dto.MemberFormDto;
import com.shop.repository.CartRepository;
import com.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest() {
        Member member = createMember();  // 회원 엔티티를 반환하는 메소드
        memberRepository.save(member);  // 회원 엔티티를 DB에 보내는 메소드.

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);  // 영속성 컨텍스트(SQL 저장소, 1차캐시)에 엔티티를 저장한 후 곧장 데이터를 DB로 보내 저장시킴.

        em.flush();  // 영속성 컨텍스트에 저장된 데이터를 강제로 DB로 보내 저장시키는 메소드.
        em.clear();

        Cart savedCart = cartRepository.findById(cart.getId())  // 저장된 장바구니 엔티티를 조회함
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(savedCart.getMember().getId(), member.getId());
        // 처음에 저장한 회원 엔티티의 id와 savedCart에 매핑된 회원 엔티티의 id를 비교함.
    }

}