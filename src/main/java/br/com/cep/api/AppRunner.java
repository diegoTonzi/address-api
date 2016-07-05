package br.com.cep.api;

import javax.validation.Validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * The type to start services
 * @author Diego Costa (diegotonzi@gmail.com)
 */
@SpringBootApplication
public class AppRunner {

	public static void main(String[] args) {
		SpringApplication.run(AppRunner.class, args);
	}
	
	@Primary
	@Bean
	public Validator validator(){
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
	
}
