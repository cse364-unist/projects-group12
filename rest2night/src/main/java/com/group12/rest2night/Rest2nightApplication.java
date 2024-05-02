package com.group12.rest2night;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Rest2nightApplication {

	public static void main(String[] args) {
		SpringApplication.run(Rest2nightApplication.class, args);
	}

}
