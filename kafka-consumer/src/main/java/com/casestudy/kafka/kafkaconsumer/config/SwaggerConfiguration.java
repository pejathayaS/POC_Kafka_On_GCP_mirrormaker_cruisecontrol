package com.casestudy.kafka.kafkaconsumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket applicationWorkFlowApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.casestudy.kafka.kafkaconsumer.controller"))
                .paths(PathSelectors.any())
                .build().enable(true)
                .apiInfo(getApiInfo());

    }
    private ApiInfo getApiInfo() {
        return new ApiInfo("Kafka-Operations",
                "Sample project to operate on kafka consumer",
                "1.0", "",
                new Contact("shobith", "", "shobithprv000@gmail.com"),
                null, null, Collections.emptyList());
    }
}
