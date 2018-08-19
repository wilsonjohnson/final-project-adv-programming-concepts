package com.snhu.app.service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.QueryBuilder;
import com.mongodb.ReflectionDBObject;

/**
 * IDAO
 */
public interface IDAO {
	public boolean create( DBObject item ) throws NullPointerException;
	public Stream< DBObject > read( DBObject find ) throws MongoException, NullPointerException;
	public DBObject update( DBObject query, DBObject update ) throws NullPointerException;
	public DBObject delete( DBObject item ) throws NullPointerException;

	public default BasicDBObjectBuilder build(){
		return BasicDBObjectBuilder.start();
	}
	public default BasicDBObjectBuilder build( Map map ){
		return BasicDBObjectBuilder.start( map );
	}
	public default BasicDBObjectBuilder build( String key, Object value ){
		return BasicDBObjectBuilder.start( key, value );
	}
	public default QueryBuilder query(){
		return QueryBuilder.start();
	}
	public default QueryBuilder queryWhere( String key ){
		return QueryBuilder.start( key );
	}
}