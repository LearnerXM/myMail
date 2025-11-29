package com.judecodes.mailadmin;

import com.judecodes.mailadmin.domain.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailAdminApplicationTests {

    @Autowired
    private AdminService adminService;

    @Test
    void contextLoads() {
    }

    @Test
    public void modifyPasswordTest() {
        String name = "admin";
        String newPassword = "A123456";
        adminService.modifyPasswordTest(name, newPassword);

    }

}
