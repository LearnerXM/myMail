package com.judecodes.mailmember.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author judecodes
 * @since 2025-11-18
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    /**
     * 测试
     */
    @RequestMapping("/test")
    public String test() {
        return "test";
    }

}
