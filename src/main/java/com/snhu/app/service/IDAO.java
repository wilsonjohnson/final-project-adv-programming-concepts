package com.snhu.app.service;

import java.util.Map;

import com.mongodb.DBObject;

/**
 * IDAO
 */
public interface IDAO {
	public boolean create( DBObject item );
	public DBObject read( DBObject find );
	public boolean update( DBObject item );
	public boolean delete( DBObject item );
}