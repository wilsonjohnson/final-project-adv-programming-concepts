package com.snhu.app;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.snhu.app.service.IDAO;
import com.snhu.app.service.InspectionsDAO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	public static MongoClient CLIENT;
	public static IDAO INSPECTIONS;
	
	public static void main( String[] args ) {
		SpringApplication.run( App.class, args );
		
		// try {
		// 	CLIENT = new MongoClient( "localhost" );
		// 	INSPECTIONS = new InspectionsDAO( CLIENT ); // Inspections Plugin
		// } catch (UnknownHostException e) {
		// 	e.printStackTrace();
		// }

		// BasicDBObject test = new BasicDBObject( "id", "5299-2016-TEST" );
		// BasicDBObject update = (BasicDBObject) test.copy();
		// BasicDBObject query = new BasicDBObject( "id", "5299-2016-ENFO" );

		// update.append("business_name", "Business Place");

		// attempt( () -> System.out.println( "Creating: " + INSPECTIONS.create( test ) ) );
		// attempt( () -> System.out.println( "Reading:  " + INSPECTIONS.read( query ).map( Objects::toString ).collect( Collectors.joining( ",\n", "[\n", "\n]")) ) );
		// attempt( () -> System.out.println( "Updating: " + INSPECTIONS.update( test, update ) ) );
		// attempt( () -> System.out.println( "Deleting: " + INSPECTIONS.delete( test ) ) );
	}

	private static void attempt( Runnable run ) {
		try {
			run.run();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
