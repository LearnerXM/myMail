package com.judecodes.mailbase.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 手机号校验器：实现 ConstraintValidator<注解类, 被校验字段类型>
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    // 国内手机号正则（同方案 1）
    private static final String PHONE_REGEX = "^1[3-9]\\d{9}$";
    // 存储注解的 allowBlank 配置
    private boolean allowBlank;

    // 初始化：获取注解的配置参数（如 allowBlank）
    @Override
    public void initialize(Phone constraintAnnotation) {
        this.allowBlank = constraintAnnotation.allowBlank();
    }

    // 核心校验逻辑：返回 true 表示校验通过，false 表示失败
    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        // 1. 若允许为空，且手机号为 null/空字符串，直接通过
        if (allowBlank && (phone == null || phone.trim().isEmpty())) {
            return true;
        }
        // 2. 非空时，校验正则
        return phone != null && phone.matches(PHONE_REGEX);
    }
}