package dev.satwant.springbootMongodb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig{
@Bean
public LocalValidatorFactoryBean validator() {
	return new LocalValidatorFactoryBean();
}
@Bean
public ValidatingMongoEventListener validatingMongoEventListener() {
	return new ValidatingMongoEventListener(validator());
}
}
