package com.judecodes.mailbase.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

// 注解作用目标：字段、方法参数等
@Target({ElementType.FIELD, ElementType.PARAMETER})
// 注解保留策略：运行时生效（需通过反射获取）
@Retention(RetentionPolicy.RUNTIME)
// 标记为校验注解，指定校验器
@Constraint(validatedBy = PhoneValidator.class)
// 允许在文档中显示注解说明
@Documented
public @interface Phone {

    // 校验失败提示信息（默认值）
    String message() default "手机号格式不正确";

    // 分组校验（默认空分组）
    Class<?>[] groups() default {};

    // 负载信息（用于传递额外数据，默认空）
    Class<? extends Payload>[] payload() default {};

    // 扩展：支持可选配置（如是否允许为空）
    boolean allowBlank() default false;
}
