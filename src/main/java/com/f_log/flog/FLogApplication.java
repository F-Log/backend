package com.f_log.flog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(FLogApplication.class, args);
    }

}
