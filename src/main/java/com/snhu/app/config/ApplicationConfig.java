package com.snhu.app.config;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * ApplicationConfig
 */
@Configuration
@ComponentScan("com.snhu.app")
public class ApplicationConfig {

	@Bean
	public MongoClient mongoClient() throws UnknownHostException {
		return new MongoClient( "localhost" );
	}
}