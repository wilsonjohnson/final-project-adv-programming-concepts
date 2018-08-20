package com.snhu.app.service;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * StocksDAO
 */
@RequestScope
@Component
public class StocksDAO implements IDAO {
	DBCollection collection;

	@Autowired
	MongoClient client;

	@Autowired
	Logger log;

	public StocksDAO() {
	}

	public StocksDAO( MongoClient client ) throws NullPointerException {
		Objects.requireNonNull( client, () -> "Client provided cannot be null" );
		log = LoggerFactory.getLogger( this.getClass() );
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
	public DBCollection getCollection() {
		return collection;
	}
	
	public int countAveragesFromTo( Double from, Double to ){
		return count( 
			queryWhere( "50-Day Simple Moving Average" )
				.greaterThan( from )
				.lessThan( to ).get() );
	}

	private DBObject tickerQuery( String ticker ) {
		return object( "Ticker", ticker );
	}

	public DBObject updateVolume( String ticker, Long volume ) throws NullPointerException {
		return update( 
				tickerQuery( ticker ), 
				queryWhere( "$set" ).is( 
						object( "Volume", volume ) 
					).get() );
	}

	public DBObject deleteTicker( String ticker ) throws NullPointerException {
		return delete( tickerQuery( ticker ) );
	}

	public DBObject updateTicker( String ticker, Map changes ) throws NullPointerException {
		return update( 
				tickerQuery( ticker ), 
				queryWhere( "$set" ).is(
						object( changes )
					).get() );
	}

	public Stream< DBObject > readTicker( String ticker ) throws NullPointerException {
		return read( tickerQuery( ticker ) );
	}

	public Stream< DBObject > readTickers( String[] tickers ) throws NullPointerException {
		return read ( queryWhere( "Ticker" ).in( tickers ).get() );
	}

	public Stream< DBObject > readIndustry( String industry ) throws NullPointerException {
		return read( queryWhere( "Industry" ).is( industry ).get() );
	}

	public Stream< String > readIndustryTickers( String industry ) throws NullPointerException {
		return read( queryWhere( "Industry" ).is( industry ).get(), object( "Ticker", 1 ) )
			.map( o -> o.get("Ticker").toString() );
	}

	public Stream< DBObject > readTopFiveByIndustry( String company ) {
		return aggregate( pipeline(
				queryWhere( "$match" ).is( object( "Company", company ) ).get(),
				queryWhere( "$sort" ).is( 
					object( "Performance (Year)", -1 ) ).get(),
				queryWhere( "$limit" ).is( 5 ).get()
			) );
	}

	public Stream< DBObject > readTopFiveByCompany( String company ) {
		return aggregate( pipeline(
				queryWhere( "$match" ).is( object( "Company", company ) ).get(),
				queryWhere( "$sort" ).is( 
					object( "Performance (Year)", -1 ) ).get(),
				queryWhere( "$limit" ).is( 5 ).get()
			) );
	}

	public Stream< DBObject > readSharesBySector( String sector ) {
		return aggregate( pipeline(
				queryWhere( "$match" ).is( object( "Sector", sector ) ).get(),
				queryWhere( "$group" ).is( 
					build( "_id", "$Industry" )
						.add( "Outstanding Shares", object(
							"$sum", "$Shares Outstanding"
						) ).get() ).get()
			) );
	}
}