package com.snhu.app.model;

import java.util.function.Function;

/**
 * Greeting
 */
public class Greeting extends Tuple< Long, String > {

	public Greeting( long id, String content ) {
		super( id, content );
	}

	public long getId(){
		return getFirst();
	}

	public String getMessage(){
		return getSecond();
	}
}
