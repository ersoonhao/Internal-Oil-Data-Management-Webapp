package com.oop.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.Collections;

@SpringBootApplication
@EnableScheduling
public class ProjectApplication {
	
	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProjectApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);
		// Webscrape.monthlyTask();
	}

}
