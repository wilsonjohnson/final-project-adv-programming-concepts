package com.snhu.app.service;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

/**
 * InspectionsDAO
 */
@RequestScope
public class InspectionsDAO implements IDAO {

	DBCollection collection;

	@Autowired
	MongoClient client;

	public InspectionsDAO() {
	}

	public InspectionsDAO( MongoClient client ) throws NullPointerException {
		Objects.requireNonNull( client, () -> "Client provided cannot be null" );
		this.client = client;
		setupCollection();
	}

	@PostConstruct
	public void postConstruct(){
		setupCollection();
	}

	private void setupCollection(){
		collection = client.getDB( "city" ).getCollection( "inspections" );
	}

	@Override
	public boolean create( DBObject item ) throws MongoException, NullPointerException  {
		Objects.requireNonNull( item, () -> "Item to create must not be null" );
		collection.insert( item );
		return true;
	}

	@Override
	public Stream< DBObject > read(DBObject find) throws NullPointerException {
		Objects.requireNonNull( find, () -> "Item to read must not be null" );
		DBCursor cursor = collection.find( find );
		return StreamSupport.stream( cursor.spliterator(), false );
	}

	@Override
	public DBObject update( DBObject query, DBObject update ) throws NullPointerException {
		Objects.requireNonNull( query, () -> "Item to update must not be null" );
		return collection.findAndModify( query, update );
	}

	@Override
	public DBObject delete( DBObject item ) throws NullPointerException {
		Objects.requireNonNull( item, () -> "Item to delete must not be null" );
		return collection.findAndRemove( item );
	}
	
}