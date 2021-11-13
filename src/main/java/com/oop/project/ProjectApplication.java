package com.oop.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import java.util.Collections;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableScheduling


public class ProjectApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ProjectApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication app=new SpringApplication(ProjectApplication.class);
        // SpringApplication.run(ProjectApplication.class, args);
        app.setDefaultProperties(Collections.singletonMap("server.port", "5000"));
        app.run(args);
    }

   
}


// public class ProjectApplication extends SpringBootServletInitializer {
	
// 	public static void main(String[] args) {
//         SpringApplication app = new SpringApplication(ProjectApplication.class);
//         app.setDefaultProperties(Collections.singletonMap("server.port", "5000"));
//         app.run(args);
// 		Webscrape.monthlyTask();
// 	}

// }
