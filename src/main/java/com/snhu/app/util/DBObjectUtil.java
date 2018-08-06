package com.snhu.app.util;

import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * DBObjectUtil
 */
public class DBObjectUtil {
	public static DBObject objectOf( Map< String, ? > map ){
		return new BasicDBObject( map );
	}
}