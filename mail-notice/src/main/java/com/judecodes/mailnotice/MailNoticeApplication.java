package com.judecodes.mailnotice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.judecodes")
@EnableDubbo
public class MailNoticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailNoticeApplication.class, args);
    }

}
