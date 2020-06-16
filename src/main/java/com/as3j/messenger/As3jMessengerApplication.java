package com.as3j.messenger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@ConfigurationPropertiesScan
@PropertySource("classpath:messages.properties")
@OpenAPIDefinition(info = @Info(title = "AS3J-Messenger",
        description = "API definition for an upcoming, cutting-edge messaging app", version = "0.1"))
public class As3jMessengerApplication {

    public static void main(String[] args) {
        SpringApplication.run(As3jMessengerApplication.class, args);
    }

}
