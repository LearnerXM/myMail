package com.judecodes.mailauth.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SmsRegisterParam {
    /**
     * 手机号码
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(
            regexp = "^1[3-9]\\d{9}$",  // 规则：以 1 开头，第 2 位 3-9，后 9 位数字
            message = "手机号格式不正确（需为 11 位国内有效号码）"
    )
    private String phone;

    /**
     * 验证码
     */
    @NotBlank
    private String code;
}
