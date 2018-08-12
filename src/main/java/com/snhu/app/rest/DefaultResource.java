package com.snhu.app.rest;

import com.snhu.app.model.Hello;
import com.snhu.app.model.Tuple;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * DefaultResource
 */
@RestController
public class DefaultResource {

	@Autowired
	Logger log;

	@RequestMapping( "/" )
	public ResponseEntity< String > defaultMapping() {
		return ResponseEntity.badRequest().body("Error");
	}

	@RequestMapping( "/hello" )
	public ResponseEntity< Hello > hello( @RequestParam("name") String name ) {
		try {
			Hello hello = new Hello();
			hello.setHello( name );
			return ResponseEntity.ok( hello );
		} catch ( Exception e ) {
			log.error( "", e );
			return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).build();
		}
	}

	@RequestMapping( "/strings" )
	public ResponseEntity< Tuple< String, String > > strings( @RequestBody Tuple< String, String > strings ) {
		try {
			return ResponseEntity.ok( strings );
		} catch ( Exception e ) {
			log.error( "", e );
			return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).build();
		}
	}
}