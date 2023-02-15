package com.wilson.sumawilsontenpo.config.properties;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
public class RedisLettuceProperties {

    @NotNull
    private Integer minIdle;
    @NotNull
    private Integer maxIdle;
    @NotNull
    private Integer maxActive;
    @NotNull
    private Integer maxWait;
    @NotNull
    private Integer commandTimeoutSeconds;
    @NotNull
    private Integer commandTimeoutReadinessSeconds;

}