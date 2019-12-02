package org.cnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Start the application
 * 
 * @author JParra
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "org.cnd" })
@PropertySources({ @PropertySource("classpath:application.properties"),
		@PropertySource(value = "file:${external.config}", ignoreResourceNotFound = true) })
@EnableMongoAuditing
@EnableMongoRepositories
public class SpringCndBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCndBaseApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
