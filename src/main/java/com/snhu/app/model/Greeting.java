package com.snhu.app.model;

import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Greeting
 */
@JsonPropertyOrder( { "id", "message" } )
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

	@Override
	@JsonIgnore
	public Long getFirst() {
		return super.getFirst();
	}

	@Override
	@JsonIgnore
	public String getSecond() {
		return super.getSecond();
	}
}
