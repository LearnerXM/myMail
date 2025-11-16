package com.judecodes.mailweb.config;

import com.judecodes.mailweb.handle.GlobalExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 公共 Web 自动配置：
 * - 扫描当前包下的全局异常处理、统一返回体等组件
 */
@AutoConfiguration
@ConditionalOnWebApplication
public class WebAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(WebAutoConfiguration.class);



    public WebAutoConfiguration() {
        log.info("MailWebAutoConfiguration initialized");
    }

    @Bean
    @ConditionalOnMissingBean
    GlobalExceptionHandler globalWebExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
