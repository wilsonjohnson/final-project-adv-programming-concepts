package com.snhu.app.model;

import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.AccessType.Type;

/**
 * Tuple
 */
@AccessType( Type.PROPERTY )
public class Tuple < FIRST, SECOND > {
	@Transient
	private final FIRST first;
	@Transient
	private final SECOND second;

	public Tuple( FIRST first, SECOND second ) {
		this.first = first;
		this.second = second;
	}

	public static < A, B > Tuple< A, B > of( A first, B second ){
		return new Tuple<>( first, second ); 
	}

	/**
	 * @return the first
	 */
	@Transient
	@JsonIgnore
	public FIRST getFirst() {
		return first;
	}

	/**
	 * @return the second
	 */
	@Transient
	@JsonIgnore
	public SECOND getSecond() {
		return second;
	}

	public < A, B > Tuple< A, B > map( Function<FIRST, A> firstMapping, Function<SECOND, B> secondMapping ){
		return of( firstMapping.apply( first ), secondMapping.apply( second ) );
	}
}
