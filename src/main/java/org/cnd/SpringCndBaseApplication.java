package org.cnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Start the application
 * @author User
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "org.cnd"})
@PropertySources({ @PropertySource("classpath:application.properties"),
	@PropertySource(value = "file:${external.config}", ignoreResourceNotFound = true) })
@EnableMongoAuditing
@EnableMongoRepositories
public class SpringCndBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCndBaseApplication.class, args);
	}

}
