package com.testTaskPoll;

import com.testTaskPoll.config.JpaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class PollApplication {

	public static void main(String[] args) {
		SpringApplication.run(new Class<?>[] {PollApplication.class, JpaConfig.class}, args);

	}
}
