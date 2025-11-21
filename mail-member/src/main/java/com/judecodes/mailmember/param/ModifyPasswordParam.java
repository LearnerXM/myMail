package com.judecodes.mailmember.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ModifyPasswordParam {

    @NotBlank(message = "token不能为空")
    String changeToken;


    @Size(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,20}$",
            message = "密码必须包含字母和数字"
    )
    @NotBlank
    String password;
}
