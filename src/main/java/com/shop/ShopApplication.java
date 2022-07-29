package com.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

}
// 스프링 시큐리티를 추가하는 것 만으로도 모든 요청(url접속)이 인증을 필요로 한다.
// 로그인기능을 위한 인증, 관리자만이 접근하도록 하기위한 인가 이 2가지를 구현할 것이다.