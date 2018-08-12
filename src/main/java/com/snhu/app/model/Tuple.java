package com.snhu.app.model;

import java.util.function.Function;

import org.springframework.data.annotation.Transient;

/**
 * Tuple
 */
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
	public FIRST getFirst() {
		return first;
	}

	/**
	 * @return the second
	 */
	@Transient
	public SECOND getSecond() {
		return second;
	}

	public < A, B > Tuple< A, B > map( Function<FIRST, A> firstMapping, Function<SECOND, B> secondMapping ){
		return of( firstMapping.apply( first ), secondMapping.apply( second ) );
	}
}
