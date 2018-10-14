package com.snhu.app.service;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static com.snhu.app.service.DBUtil.SortOrder.DESCENDING;

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
        Objects.requireNonNull(client, "Client provided cannot be null");
		log = LoggerFactory.getLogger( this.getClass() );
		this.client = client;
		setupCollection();
	}

	@PostConstruct
	public void postConstruct(){
		setupCollection();
	}

	/**
	 * Init method to setup this DAO's collection
	 */
	private void setupCollection(){
		collection = client.getDB( "market" ).getCollection( "stocks" );
	}

	@Override
	public DBCollection getCollection() {
		return collection;
	}
	
	/**
	 * Counts the averages within a specified range
	 */
	public int countAveragesFromTo( Double from, Double to ){
		return count( 
			queryWhere( "50-Day Simple Moving Average" )
				.greaterThan( from )
				.lessThan( to ).get() );
	}

	/**
	 * Creates a ticker object:
	 * <pre>
	 * { "Ticker": ticker }
	 * </pre>
	 */
	private DBObject tickerQuery( String ticker ) {
		return object( "Ticker", ticker );
	}

	/**
	 * Updates the Volume for a specified ticker
	 */
	public DBObject updateVolume( String ticker, Long volume ) throws NullPointerException {
		return update( 
				tickerQuery( ticker ), 
				queryWhere( "$set" ).is( 
						object( "Volume", volume ) 
					).get() );
	}

	/**
	 * Deletes all documents with matching tickers
	 */
	public DBObject deleteTicker( String ticker ) throws NullPointerException {
		return delete( tickerQuery( ticker ) );
	}

	/**
	 * Applies the changes to any document with matching tickers
	 */
	public DBObject updateTicker( String ticker, Map changes ) throws NullPointerException {
		return update( 
				tickerQuery( ticker ), 
				queryWhere( "$set" ).is(
						object( changes )
					).get() );
	}

	/**
	 * Selects all documents with matching tickers
	 */
	public Stream< DBObject > readTicker( String ticker ) throws NullPointerException {
        return find(tickerQuery(ticker));
	}

	/**
	 * Selects all documents that have tickers in a supplied array of tickers
	 */
	public Stream< DBObject > readTickers( String[] tickers ) throws NullPointerException {
        return find(queryWhere("Ticker").in(tickers).get());
	}

	/**
	 * selects all documents that matches a specific industry
	 */
	public Stream< DBObject > readIndustry( String industry ) throws NullPointerException {
        return find(queryWhere("Industry").is(industry).get());
	}

	/**
	 * Selects all tickers for a specified industry
	 */
	public Stream< String > readIndustryTickers( String industry ) throws NullPointerException {
        return find(queryWhere("Industry").is(industry).get(), object("Ticker", 1))
			.map( o -> o.get("Ticker").toString() );
	}

	/**
	 * Lists the top 5 stocks based on arbitrarily selected criteria for an Industry
	 * performs the following mongodb aggregate query:
	 * <pre>
	 * aggregate([
	 * 	{ $match: { "Industry": industry } },
	 * 	{ $sort: { "Performance (Year)": -1 } },
	 * 	{ $limit: 5 }
	 * ])
	 * </pre>
	 */
	public Stream< DBObject > readTopFiveByIndustry( String industry ) {
        return aggregate(pipeline(
                $match(object("Industry", industry)),
                $sort(pair("Performance (Year)", DESCENDING)),
                $limit(5)
        ));
	}

	/**
	 * Lists the top 5 stocks based on arbitrarily selected criteria for a Company
	 * performs the following mongodb aggregate query:
	 * <pre>
	 * aggregate([
	 * 	{ $match: { "Company": company } },
	 * 	{ $sort: { "Performance (Year)": -1 } },
	 * 	{ $limit: 5 }
	 * ])
	 * </pre>
	 */
	public Stream< DBObject > readTopFiveByCompany( String company ) {
		return aggregate( pipeline(
                $match(object("Company", company)),
                $sort(pair("Performance (Year)", DESCENDING)),
                $limit(5)
			) );
	}

	/**
	 * Lists Industries and the sum of the shares of all documents in them by Sector
	 * 
	 * performs the following mongodb aggregate query:
	 * <pre>
	 * aggregate([
	 * 	{ $match: { "Sector": sector } },
	 * 	{ $group: { 
	 * 		_id: "$Industry",
	 * 		"Outstanding Shares": {
	 * 			$sum: "$Shares Outstanding" 
	 * 		}
	 * 	}
	 * }])
	 * </pre>
	 */
	public Stream< DBObject > readSharesBySector( String sector ) {
		return aggregate( pipeline(
                $match(object("Sector", sector)),
                $group(
                        pair("_id", "$Industry"),
                        pair("Outstanding Shares", $sum("$Shares Outstanding"))
                )
			) );
	}
}