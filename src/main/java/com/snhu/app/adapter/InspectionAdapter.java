package com.snhu.app.adapter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.snhu.app.model.Inspection;
import com.snhu.app.util.FormatUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * InspectionAdapter
 */
@Component
public class InspectionAdapter implements Adapter<Inspection> {

	private Logger log = LoggerFactory.getLogger( this.getClass() );

	private static final DateTimeFormatter FORMAT =  DateTimeFormatter.ofPattern("MMM dd yyyy");

	@Override
	public Optional< Inspection > toJava( DBObject object ) {
		try {
			ObjectMapper mapper = new ObjectMapper()
				.registerModule( new Jdk8Module() )
				.registerModule( new JavaTimeModule() )
				.registerModule( new ParameterNamesModule() );

			Object date = object.get("date");
			if( date instanceof String ){
				String temp = (String) date;
				if( temp == null || temp.isEmpty() ){
					object.put( "date", null );
				} else {
					object.put( "date", LocalDate.parse( temp, FORMAT ).atStartOfDay().format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ) );
				}
			} else if( date instanceof Date ){
				Date temp = (Date) date;
				object.put( "date", Timestamp.from( temp.toInstant() ).toLocalDateTime().format( DateTimeFormatter.ISO_LOCAL_DATE_TIME ) );
			}
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