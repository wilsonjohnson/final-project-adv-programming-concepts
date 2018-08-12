package com.snhu.app.service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.ReflectionDBObject;

/**
 * IDAO
 */
public interface IDAO {
	public boolean create( DBObject item ) throws NullPointerException;
	public Stream< DBObject > read( DBObject find ) throws MongoException, NullPointerException;
	public DBObject update( DBObject query, DBObject update ) throws NullPointerException;
	public DBObject delete( DBObject item ) throws NullPointerException;
}