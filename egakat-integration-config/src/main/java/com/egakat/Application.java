package com.egakat;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// @formatter:off
		new SpringApplicationBuilder(Application.class)
		.build()
		.run(args);
		// @formatter:on
	}
}
