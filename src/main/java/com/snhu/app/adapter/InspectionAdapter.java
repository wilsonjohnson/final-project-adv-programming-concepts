package com.snhu.app.adapter;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.snhu.app.model.Inspection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * InspectionAdapter
 */
@Component
public class InspectionAdapter implements Adapter<Inspection> {

	private Logger log = LoggerFactory.getLogger( this.getClass() );

	@Override
	public Optional< Inspection > toJava( DBObject object ) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return Optional.ofNullable( mapper.readValue( object.toString() , Inspection.class ) );
		} catch ( Exception e ){
			log.error( "", e );
			return Optional.empty();
		}
	}

	@Override
	public DBObject toDbObject(Inspection object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return (DBObject) JSON.parse( mapper.writeValueAsString( object ) );
		} catch ( JsonProcessingException e ) {
			log.error( "", e );
			return null;
		}
	}

	
}