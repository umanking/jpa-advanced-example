package com.example.demo;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // hibernate 5 moulde의 기본 설정은 proxy 인 친구들은 데이터를 뿌리지 않는다.
    // 언제? lazy 로딩을 강제 초기화 하게 되면 그제서야 뿌린다.
    // ※ lazy 로딩은 proxy다
    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }
}
