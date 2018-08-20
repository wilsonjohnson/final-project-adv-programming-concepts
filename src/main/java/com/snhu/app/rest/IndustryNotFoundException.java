package com.snhu.app.rest;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TickerNotFoundException
 */
@ResponseStatus( code = HttpStatus.NOT_FOUND)
public class IndustryNotFoundException extends RuntimeException{
	String industry;
	public IndustryNotFoundException( String industry ){
		super( "No stocks found for Industry: " + industry );
		this.industry = industry;
	}

	public static Supplier<IndustryNotFoundException> supply( String industry ){
		return () -> new IndustryNotFoundException(industry);
	} 
}