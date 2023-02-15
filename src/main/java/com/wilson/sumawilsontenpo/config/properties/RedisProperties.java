package com.wilson.sumawilsontenpo.config.properties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

    @NotBlank
    private String host;
    @NotNull
    private Integer port;
    private String password;

}