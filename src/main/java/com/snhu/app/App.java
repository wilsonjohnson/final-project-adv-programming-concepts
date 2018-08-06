package com.snhu.app;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import com.snhu.app.service.InspectionsDAO;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import static java.util.concurrent.TimeUnit.SECONDS;

public class App {
	public static MongoClient CLIENT;
	public static InspectionsDAO INSPECTIONS;

	public static void create_document(BasicDBObject doc) {
		try {
			MongoClient mongoClient = new MongoClient( "localhost" );
			DB db = mongoClient.getDB( "city" );
			DBCollection coll = db.getCollection("inspections");
			StreamSupport.stream( coll.find().spliterator(), false ).map( Objects::toString ).forEach( System.out::println );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main( String[] args ) {
		try {
			CLIENT = new MongoClient( "localhost" );
			INSPECTIONS = new InspectionsDAO( CLIENT );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		BasicDBObject test = new BasicDBObject( "id", "5299-2016-TEST" );
		BasicDBObject update = (BasicDBObject) test.copy();
		BasicDBObject query = new BasicDBObject( "id", "5299-2016-ENFO" );

		update.append("business_name", "Business Place");

		System.out.println( "Creating: " + INSPECTIONS.create( test ) );
		System.out.println( "Creating: " + INSPECTIONS.read( query ).map( Objects::toString ).collect( Collectors.joining( ",\n", "[\n", "\n]")) );
		System.out.println( "Creating: " + INSPECTIONS.update( test, update ) );
		System.out.println( "Creating: " + INSPECTIONS.read( test ).map( Objects::toString ).collect( Collectors.joining( ",\n", "[\n", "\n]")) );
		System.out.println( "Creating: " + INSPECTIONS.delete( test ) );
	}
}
