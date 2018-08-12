package com.snhu.app.model;

import org.springframework.boot.jackson.JsonComponent;

/**
 * Hello
 */
@JsonComponent
public class Hello {
	private String hello;

	/**
	 * @return the hello
	 */
	public String getHello() {
		return hello;
	}
	
	/**
	 * @param hello the hello to set
	 */
	public void setHello(String hello) {
		this.hello = hello;
	}
}