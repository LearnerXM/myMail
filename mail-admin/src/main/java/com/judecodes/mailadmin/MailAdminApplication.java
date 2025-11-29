package com.judecodes.mailadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.judecodes")
public class MailAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailAdminApplication.class, args);
    }

}
