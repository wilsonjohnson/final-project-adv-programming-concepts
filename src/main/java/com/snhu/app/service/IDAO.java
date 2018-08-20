package com.snhu.app.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteResult;

/**
 * IDAO
 */
public interface IDAO {
	public static final DBObject SUCCESS = new BasicDBObject( "success", true );
	public DBCollection getCollection();

	/**
	 * Generic create method for all classes implementing IDAO
	 */
	public default boolean create( DBObject item ) throws MongoException, NullPointerException  {
		Objects.requireNonNull( item, () -> "Item to create must not be null" );
		getCollection().insert( item );
		return true;
	}

	/**
	 * Generic read method for all classes implementing IDAO
	 */
	public default Stream< DBObject > read( DBObject find ) throws NullPointerException {
		Objects.requireNonNull( find, () -> "Item to read must not be null" );
		return StreamSupport.stream( getCollection().find( find ).spliterator(), false ).peek( map -> map.removeField( "_id" ) );
	}

	/**
	 * Generic read and project method for all classes implementing IDAO
	 */
	public default Stream< DBObject > read( DBObject find,  DBObject project  ) throws NullPointerException {
		Objects.requireNonNull( find, () -> "Item to read must not be null" );
		return StreamSupport.stream( getCollection().find( find, project ).spliterator(), false ).peek( map -> map.removeField( "_id" ) );
	}

	/**
	 * Generic count method for all classes implementing IDAO
	 */
	public default int count( DBObject find ) throws NullPointerException {
		Objects.requireNonNull( find, () -> "Item to read must not be null" );
		return getCollection().find( find ).count();
	}

	/**
	 * Generic update method for all classes implementing IDAO
	 */
	public default DBObject update( DBObject query, DBObject update ) throws NullPointerException {
		Objects.requireNonNull( query, () -> "Item to update must not be null" );
		WriteResult result = getCollection().update( query, update, false, true );
		return build( "msg", result.getError() )
					.add( "changed", result.getN()).get();
	}

	/**
	 * Generic delete method for all classes implementing IDAO
	 */
	public default DBObject delete( DBObject item ) throws NullPointerException {
		Objects.requireNonNull( item, () -> "Item to delete must not be null" );
		WriteResult result = getCollection().remove( item );
		return build( "msg", result.getError() )
					.add( "changed", result.getN()).get();
	}
	
	/**
	 * Generic aggregate method for all classes implementing IDAO
	 */
	public default Stream< DBObject > aggregate( List< DBObject > pipeline ) {
		return StreamSupport.stream( getCollection().aggregate( pipeline ).results().spliterator(), false );
	}

	/**
	 * Helper method for creating BasicDBObjectBuilders
	 */
	public default BasicDBObjectBuilder build(){
		return BasicDBObjectBuilder.start();
	}

	/**
	 * Helper method for creating BasicDBObjectBuilders
	 */
	public default BasicDBObjectBuilder build( Map map ){
		return BasicDBObjectBuilder.start( map );
	}

	/**
	 * Helper method for creating BasicDBObjectBuilders
	 */
	public default BasicDBObjectBuilder build( String key, Object value ){
		return BasicDBObjectBuilder.start( key, value );
	}

	/**
	 * Helper method for creating BasicDBObjects
	 */
	public default DBObject object( String key, Object value ){
		return build( key, value ).get();
	}

	/**
	 * Helper method for creating BasicDBObjects
	 */
	public default DBObject object( Map map ){
		return build( map ).get();
	}
	
	/**
	 * Helper method for creating QueryBuilders
	 */
	public default QueryBuilder query(){
		return QueryBuilder.start();
	}

	/**
	 * Helper method for creating QueryBuilders
	 */
	public default QueryBuilder queryWhere( String key ){
		return QueryBuilder.start( key );
	}

	/**
	 * Helper method for creating Aggregation pipelines
	 */
	public default List< DBObject > pipeline( DBObject ...stages ){
		return Arrays.asList( stages );
	}
}