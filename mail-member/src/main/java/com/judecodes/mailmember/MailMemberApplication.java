package com.judecodes.mailmember;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.judecodes")
@EnableDubbo
public class MailMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailMemberApplication.class, args);
    }

}
