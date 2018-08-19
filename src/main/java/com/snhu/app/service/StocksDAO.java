package com.snhu.app.service;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * StocksDAO
 */
@RequestScope
@Component
public class StocksDAO implements IDAO {

	private static final DBObject SUCCESS = new BasicDBObject( "success", true );
	DBCollection collection;

	@Autowired
	MongoClient client;

	public StocksDAO() {
	}

	public StocksDAO( MongoClient client ) throws NullPointerException {
		Objects.requireNonNull( client, () -> "Client provided cannot be null" );
		this.client = client;
		setupCollection();
	}

	@PostConstruct
	public void postConstruct(){
		setupCollection();
	}

	private void setupCollection(){
		collection = client.getDB( "market" ).getCollection( "stocks" );
	}

	@Override
	public boolean create( DBObject item ) throws MongoException, NullPointerException  {
		Objects.requireNonNull( item, () -> "Item to create must not be null" );
		collection.insert( item );
		return true;
	}

	@Override
	public Stream< DBObject > read( DBObject find ) throws NullPointerException {
		Objects.requireNonNull( find, () -> "Item to read must not be null" );
		DBCursor cursor = collection.find( find );
		return StreamSupport.stream( cursor.spliterator(), false );
	}

	@Override
	public DBObject update( DBObject query, DBObject update ) throws NullPointerException {
		Objects.requireNonNull( query, () -> "Item to update must not be null" );
		collection.update( query, update, false, true );
		return SUCCESS;
	}

	@Override
	public DBObject delete( DBObject item ) throws NullPointerException {
		Objects.requireNonNull( item, () -> "Item to delete must not be null" );
		collection.remove( item );
		return SUCCESS;
	}
	
	private DBObject tickerQuery( String ticker ) {
		return new BasicDBObject( "Ticker", ticker );
	}

	public DBObject updateVolume( String ticker, Long volume ) throws NullPointerException {
		DBObject update = BasicDBObjectBuilder.start( "$set", new BasicDBObject( "Volume", volume ) ).get();
		return update( tickerQuery( ticker ), update );
	}

	public DBObject deleteTicker( String ticker ) throws NullPointerException {
		return delete( tickerQuery( ticker ) );
	}

	public Stream< DBObject > readTicker( String ticker ) throws NullPointerException {
		return read( tickerQuery( ticker ) );
	}
}