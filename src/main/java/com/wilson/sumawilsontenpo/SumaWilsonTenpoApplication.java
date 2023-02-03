package com.wilson.sumawilsontenpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@EnableJpaRepositories(basePackages="com.wilson.sumawilsontenpo.application.port.output.repository")
@EntityScan(basePackages="com.wilson.sumawilsontenpo.application.port.output.entity")
@SpringBootApplication
@ConfigurationPropertiesScan
public class SumaWilsonTenpoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SumaWilsonTenpoApplication.class, args);
	}

}
