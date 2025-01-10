package com.beboilerplate;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing // JPA Auditing 어노테이션 전체 활성화하는 기능
public class BeBoilerPlateApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeBoilerPlateApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
