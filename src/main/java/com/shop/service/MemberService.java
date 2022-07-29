package com.shop.service;
// 비즈니스 로직을 담당하는 서비스 계층 클래스이다.

import com.shop.entity.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.userdetails.User;  // 해당 클래스는 바로 아래 UserDetails인터페이스를 구현하고 있음
import org.springframework.security.core.userdetails.UserDetails;  // 해당 인터페이스는 회원의 정보를 담기 위해 사용됨.
import org.springframework.security.core.userdetails.UserDetailsService;  // 해당 인터페이스는 DB에서 회원정보를 가져오는 역할을 함.
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@Transactional  // 로직을 처리하다가 에러가 발생했다면, 변경된 데이터를 로직 수행 이전의 상태로 콜백 해준다.
@RequiredArgsConstructor
public class MemberService implements UserDetailsService{  // MemberService 클래스가 UserDetailsService 인터페이스를 구현함.

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
        // save(member)가 호출되면 엔티티인 member가 가지고있던 데이터가 DB에 저장된다.
        // 그리고 해당 엔티티를 다시 반환한다.
    }

    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
            // 이미 가입된 회원의 경우 해당 예외를 발생시킨다. 이미 동일한 이메일이 DB에 있다? 내가 임의로 해당 예외객체를 생성한다!
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // UserDetailsService 인터페이스의 메소드이다.
        Member member = memberRepository.findByEmail(email);

        if (member == null) {  // DB로부터 회원정보를 가져오는 것이므로 데이터가 들어있어야지 오류가 안난다.
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
        // 회원정보를 조회하여 사용자의 정보와 권한을 갖는 UserDetails 인터페이스를 반환함.
        // 정확히는 UserDetails 인터페이스를 구현하고 있는 User 객체를 반환함. User 객체 생성을 위해 생성자로 회원의 email, password, role을 파라미터로 넘겨줌.
    }
}
