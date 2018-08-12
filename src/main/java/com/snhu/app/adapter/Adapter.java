package com.snhu.app.adapter;

import java.util.Optional;

import com.mongodb.DBObject;

/**
 * Adapter
 */
public interface Adapter <TYPE> {

	public Optional< TYPE > toJava( DBObject object );
	public DBObject toDbObject( TYPE object );
}
