package com.snhu.app.config;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.snhu.app.service.InspectionsDAO;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ApplicationConfig
 */
@Configuration
public class ApplicationConfig {

	@Bean
	public MongoClient mongoClient() throws UnknownHostException {
		return new MongoClient( "localhost" );
	}
}