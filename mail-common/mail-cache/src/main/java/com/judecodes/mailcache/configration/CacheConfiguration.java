package com.judecodes.mailcache.configration;



import com.alicp.jetcache.anno.config.EnableMethodCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.autoconfigure.AutoConfiguration;



@AutoConfiguration
@EnableMethodCache(basePackages = "com.judecodes")  // 不写 basePackages 时，默认扫描全局（够用）
public class CacheConfiguration {
    private static final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

    public CacheConfiguration() {
        log.info("CacheConfiguration initialized: JetCache + Redisson enabled");
    }
}
