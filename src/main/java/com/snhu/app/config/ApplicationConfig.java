package com.snhu.app.config;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import com.snhu.app.service.StocksDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * ApplicationConfig
 */
@Configuration
@ComponentScan("com.snhu.app")
public class ApplicationConfig {

	@Autowired
	MongoClient client;

	@Bean
	@Scope( "prototype" )
	public MongoClient mongoClient() throws UnknownHostException {
		return new MongoClient( "localhost" );
	}

	@Bean( "appStart" )
	@Scope( "prototype" )
	public StocksDAO stocksDAO() {
		return new StocksDAO( client );
	}

	@Bean
	@Scope( "prototype" )
	public Logger logger( InjectionPoint injectionPoint ) {
		return LoggerFactory.getLogger( injectionPoint.getMember().getDeclaringClass() );
	}

}