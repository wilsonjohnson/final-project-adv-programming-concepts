package com.snhu.app.service;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteResult;
import com.snhu.app.util.DBObjectUtil;

/**
 * InspectionsDAO
 */
public class InspectionsDAO implements IDAO {

	DBCollection collection;

	public InspectionsDAO( MongoClient client ){
		Objects.requireNonNull( client, () -> "Client provided cannot be null" );
		collection = client.getDB( "city" ).getCollection( "inspections" );
		
	}

	@Override
	public boolean create( DBObject item ) {
		Objects.requireNonNull( item, () -> "Item to create must not be null" );
		WriteResult result = collection.insert( item );
		if ( result.getError() != null && !result.getError().isEmpty() ) {
			throw new MongoException( result.getError() );
		}
		return true;
	}

	@Override
	public Stream< DBObject > read(DBObject find) {
		Objects.requireNonNull( find, () -> "Item to read must not be null" );
		DBCursor cursor = collection.find( find );
		return StreamSupport.stream( cursor.spliterator(), false );
	}

	@Override
	public DBObject update( DBObject query, DBObject update ) {
		Objects.requireNonNull( query, () -> "Item to update must not be null" );
		return collection.findAndModify( query, update );
	}

	@Override
	public DBObject delete( DBObject item ) {
		Objects.requireNonNull( item, () -> "Item to delete must not be null" );
		return collection.findAndRemove( item );
	}
	
}