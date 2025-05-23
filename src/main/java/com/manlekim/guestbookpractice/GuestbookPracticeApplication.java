package com.manlekim.guestbookpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GuestbookPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuestbookPracticeApplication.class, args);
    }

}
