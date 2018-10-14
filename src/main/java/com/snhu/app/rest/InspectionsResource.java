package com.snhu.app.rest;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.snhu.app.adapter.InspectionAdapter;
import com.snhu.app.model.Inspection;
import com.snhu.app.service.InspectionsDAO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * InpsectionsResource
 */
@RestController
@RequestMapping( "/inspections" )
public class InspectionsResource {

	@Autowired
	InspectionsDAO inspectionsDAO;

	@Autowired
	InspectionAdapter inspectionAdapter;

	@Autowired
	Logger log;

	@PostMapping( path = "/create", consumes = "application/json", produces = "text/plain" )
	public ResponseEntity< String > create( @RequestBody String item ){
		try {
			log.debug( "Recieved: {}",  item );
			return ResponseEntity.ok().body( "" + inspectionsDAO.create( ( DBObject ) JSON.parse( item ) ) );
		} catch ( Exception e ) {
			log.error( "", e );
			return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( e.getMessage() );
		}
	}

	@GetMapping( "/read" )
	public ResponseEntity< List< Inspection > > read( @RequestParam("business_name") String businessName ){
		try {
			log.debug( "Looking Up: {}",  businessName );
			DBObject object = BasicDBObjectBuilder.start( "business_name", businessName ).get();
            List<Inspection> inspections = inspectionsDAO.find(object)
				.peek( o -> log.debug( "Found: {}", o ) )
				.map( inspectionAdapter::toJava )
				.filter( Optional::isPresent )
				.map( Optional::get )
				.collect( Collectors.toList() );
			return ResponseEntity.ok().body( inspections );
		} catch ( Exception e ) {
			log.error( "", e );
			return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).build();
		}
	}

	@GetMapping( "/update" )
	public ResponseEntity< Inspection > update(  @RequestParam("id") String id,  @RequestParam("result") String result  ){
		try {
			BasicDBObject query = new BasicDBObject( "id", id );
			DBObject update = BasicDBObjectBuilder.start( "$set", new BasicDBObject( "result", result ) ).get();
			Optional< Inspection > inspection = inspectionAdapter.toJava( inspectionsDAO.update( query, update ) );
			inspection.orElseThrow( NotFoundException::new );
			return ResponseEntity.ok().body( inspection.get() );
		} catch ( NotFoundException e ){
			log.error( "", e );
			return ResponseEntity.notFound().build();
		} catch ( Exception e ) {
			log.error( "", e );
			return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).build();
		}
	}

	@GetMapping( "/delete" )
	public ResponseEntity< Inspection > delete( @RequestParam("id") String id ){
		try {
			BasicDBObject item = new BasicDBObject( "id", id );
			Optional< Inspection > inspection = inspectionAdapter.toJava( inspectionsDAO.delete( item ) );
			inspection.orElseThrow( NotFoundException::new );
			return ResponseEntity.ok().body( inspection.get() );
		} catch ( NotFoundException e ){
			log.error( "", e );
			return ResponseEntity.notFound().build();
		} catch ( Exception e ) {
			log.error( "", e );
			return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).build();
		}
	}
}