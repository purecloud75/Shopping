package com.shop.entity;
// 회원가입당시 적었던 정보, 즉 회원 정보를 저장하는 엔티티이다.

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;


@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)  // 회원은 이메일을 통해 유일하게 구분해야 하기에, 값은값이 DB에 못 들어오도록 unique 속성을 지정함.
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)  // 자바의 enum 타입을 엔티티의 속성으로 지정 가능.
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        // Member 엔티티를 생성하는 메소드. 이 메소드로 관리를 한다면 코드가 변경되더라도 한 군데만 수정하면 되는 이점이20-[ 있음.
        Member member = new Member();

        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());

        String password = passwordEncoder.encode(memberFormDto.getPassword());
        // 스프링 시큐리티 설정 클래스에 등록한 BCryptPasswordEncoder Bean을 인자로 넘겨서 비밀번호를 암호화한다.

        member.setPassword(password);
        member.setRole(Role.USER);

        return member;
    }
}
