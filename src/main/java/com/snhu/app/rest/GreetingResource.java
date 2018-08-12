package com.snhu.app.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import com.snhu.app.model.Greeting;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * GreetingResource
 */
@RestController
public class GreetingResource {
	private static final AtomicLong COUNT = new AtomicLong();
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern( "yyyyMMdd HH:mm" );

	private static String greet( String name ) {
		return "Hello, " + name + "!";
	}

	@RequestMapping( "/greeting" )
	public Greeting greeting( @RequestParam( value="name", defaultValue ="World") String name ) {
		return new Greeting( COUNT.incrementAndGet(), greet( name ) );
	}

	@RequestMapping( "/currentTime" )
	public Greeting getCurrentTime() {
		return new Greeting( COUNT.incrementAndGet(), LocalDateTime.now().format( FORMATTER ) );
	}
}
