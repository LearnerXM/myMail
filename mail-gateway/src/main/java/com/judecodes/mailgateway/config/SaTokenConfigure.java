package com.judecodes.mailgateway.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [Sa-Token 权限认证] 全局配置类
 */
/**
 * [Sa-Token 权限认证] 配置类
 * @author click33
 */
@Configuration
@Slf4j
public class SaTokenConfigure {
    // 注册 Sa-Token全局过滤器
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截地址
                .addInclude("/**")    // 拦截全部 path
                // 全局放行地址（连 Sa-Filter 都不进）
                .addExclude(
                        "/favicon.ico",
                        "/auth/**",
                        "/doc/**",
                        "/goods/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                )
                // 鉴权方法：每次请求都会进这里
                .setAuth(obj -> {
                    log.info("网关鉴权生效");

                    // 1. 登录校验：除了开放接口 & 管理员登录接口，其他都要求已登录
                    SaRouter.match("/**")
                            .notMatch(
                                    "/auth/**",
                                    "/doc/**",
                                    "/goods/**",
                                    "/v3/api-docs/**",
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/admin/login",
                                    "/mail-admin/** "// 管理员登录接口不需要已登录
                            )
                            .check(r -> StpUtil.checkLogin());

                    // 2. 角色校验：访问 /admin/** 需要 admin 角色，但登录接口除外
//                    SaRouter.match("/admin/**")
//                            .notMatch("/admin/login")
//                            .check(r -> StpUtil.checkRole("admin"));

                    // TODO 其他模块的角色/权限可以继续在这里加
                })
                // 异常处理方法：每次 setAuth 抛异常时进入
                .setError(this::getSaResult);
    }

    private SaResult getSaResult(Throwable throwable) {
        switch (throwable) {
            case NotLoginException notLoginException:
                log.error("请先登录");
                return SaResult.error("请先登录");
            case NotRoleException notRoleException:
                log.error("非职责角色！");
                return SaResult.error("非职责角色，请联系管理员！");
            case NotPermissionException notPermissionException:
                log.error("您无权限进行此操作！");
                return SaResult.error("您无权限进行此操作！");
            default:
                return SaResult.error(throwable.getMessage());
        }
    }
}
