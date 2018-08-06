package com.snhu.app.service;

import java.util.Map;
import java.util.stream.Stream;

import com.mongodb.DBObject;

/**
 * IDAO
 */
public interface IDAO {
	public boolean create( DBObject item );
	public Stream< DBObject > read( DBObject find );
	public DBObject update( DBObject query, DBObject update );
	public DBObject delete( DBObject item );
}