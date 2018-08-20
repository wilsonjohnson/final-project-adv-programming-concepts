package com.snhu.app.rest;

import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * TickerNotFoundException
 */
@ResponseStatus( code = HttpStatus.NOT_FOUND)
public class TickerNotFoundException extends RuntimeException{
	String ticker;
	public TickerNotFoundException( String ticker ){
		super( "No stocks found for ticker symbol: " + ticker );
		this.ticker = ticker;
	}

	public static Supplier<TickerNotFoundException> supply( String ticker ){
		return () -> new TickerNotFoundException(ticker);
	} 
}