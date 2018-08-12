package com.snhu.app.rest;

import com.mongodb.DBObject;
import com.snhu.app.service.InspectionsDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * InpsectionsResource
 */
@RestController
@RequestMapping( "/inspections" )
public class InpsectionsResource {

	@Autowired
	InspectionsDAO inspectionsDAO;

	@PostMapping( path = "/create", consumes = "application/json", produces = "application/json" )
	public ResponseEntity<Boolean> create( DBObject item ){
		try {
			return ResponseEntity.ok().body( inspectionsDAO.create( item ) );
		} catch ( Exception e ) {
			return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( false );
		}
	}

	@GetMapping( "/read" )
	public ResponseEntity<String> read(){
		return ResponseEntity.status( HttpStatus.NOT_IMPLEMENTED ).body( "NOT IMPLEMENTED" );
	}

	@GetMapping( "/update" )
	public ResponseEntity<String> update(){
		return ResponseEntity.status( HttpStatus.NOT_IMPLEMENTED ).body( "NOT IMPLEMENTED" );
	}

	@GetMapping( "/delete" )
	public ResponseEntity<String> delete(){
		return ResponseEntity.status( HttpStatus.NOT_IMPLEMENTED ).body( "NOT IMPLEMENTED" );
	}
}