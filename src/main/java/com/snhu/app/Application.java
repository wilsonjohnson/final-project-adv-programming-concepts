package com.snhu.app;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.snhu.app.service.IDAO;
import com.snhu.app.service.InspectionsDAO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("application.properties")
public class Application {	
	public static void main( String[] args ) {
		SpringApplication.run( Application.class, args );
	}
}
