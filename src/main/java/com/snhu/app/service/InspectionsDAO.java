package com.snhu.app.service;

import java.util.Map;
import java.util.Objects;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
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
	public DBObject read(DBObject find) {
		return null;
	}

	@Override
	public boolean update(DBObject item) {
		return false;
	}

	@Override
	public boolean delete(DBObject item) {
		return false;
	}
	
}