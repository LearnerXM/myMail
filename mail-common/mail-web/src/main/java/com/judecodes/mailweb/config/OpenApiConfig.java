package com.judecodes.mailweb.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
/**
 * 全局 OpenAPI/Swagger 配置
 */
@AutoConfiguration
public class OpenApiConfig {

    @Bean
    public OpenAPI mailOpenAPI() {
        // 这里假设你用的是 Sa-Token，token 放在 header，比如 "satoken"
        final String securitySchemeName = "satoken";

        return new OpenAPI()
                .info(new Info()
                        .title("Mail 系统接口文档")
                        .description("Mail 项目统一 API 文档")
                        .version("1.0.0"))
                // 全局安全设置：让所有接口默认带上 security
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("session") // 你用啥就写啥，不影响功能
                        )
                );
    }
}
