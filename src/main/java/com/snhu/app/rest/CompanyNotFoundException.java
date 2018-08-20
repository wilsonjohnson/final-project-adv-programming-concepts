package com.snhu.app.rest;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TickerNotFoundException
 */
@ResponseStatus( code = HttpStatus.NOT_FOUND)
public class CompanyNotFoundException extends RuntimeException{
	String industry;
	public CompanyNotFoundException( String company ){
		super( "No stocks found for Company: " + company );
		this.industry = company;
	}

	public static Supplier<CompanyNotFoundException> supply( String company ){
		return () -> new CompanyNotFoundException(company);
	} 
}