package com.wilson.sumawilsontenpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableCaching
@EnableFeignClients
@SpringBootApplication
@ConfigurationPropertiesScan
@EntityScan(basePackages = "com.wilson.sumawilsontenpo.entity")
@EnableJpaRepositories(basePackages = "com.wilson.sumawilsontenpo.application.port.output.repository")
public class SumaWilsonTenpoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SumaWilsonTenpoApplication.class, args);
    }

}
