package com.snhu.app.rest;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.snhu.app.service.InspectionsDAO;

import org.slf4j.Logger;
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

	@Autowired
	Logger log;

	@PostMapping( path = "/create", consumes = "application/json", produces = "application/json" )
	public ResponseEntity< String > create( String item ){
		try {
			log.debug( "Recieved: {}",  item );
			return ResponseEntity.ok().body( "" + inspectionsDAO.create( ( DBObject ) JSON.parse( item ) ) );
		} catch ( Exception e ) {
			log.error( "", e );
			return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( e.getMessage() );
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