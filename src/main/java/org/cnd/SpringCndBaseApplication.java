package org.cnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Start the application
 * @author User
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "org.cnd"})
@PropertySources({ @PropertySource("classpath:application.properties"),
	@PropertySource(value = "file:${external.config}", ignoreResourceNotFound = true) })
public class SpringCndBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCndBaseApplication.class, args);
	}

}
