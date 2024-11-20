package com.gates.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication(scanBasePackages = {"com.gates.comments"})
@EntityScan(basePackages = {"com.gates.comments.model"})
@EnableJpaRepositories(basePackages = {"com.gates.comments.repository"})
@ComponentScan(basePackages = {"com.gates.comments.service"})
@ComponentScan(basePackages = {"com.gates.comments.security"})
@ComponentScan(basePackages = {"com.gates.comments.config"})
public class CommetsAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommetsAppApplication.class, args);
	}


	@Bean
	public CommonsRequestLoggingFilter logFilter() {
		CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
		filter.setIncludeQueryString(true);
		filter.setIncludePayload(true);
		filter.setMaxPayloadLength(10000);
		filter.setIncludeHeaders(false);
		filter.setAfterMessagePrefix("REQUEST DATA : ");
		return filter;
	}
}
