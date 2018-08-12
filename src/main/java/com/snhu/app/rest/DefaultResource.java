package com.snhu.app.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DefaultResource
 */
@RestController
public class DefaultResource {

	@RequestMapping( "/" )
	public ResponseEntity< String > defaultMapping() {
		return ResponseEntity.badRequest().body("Error");
	}
}