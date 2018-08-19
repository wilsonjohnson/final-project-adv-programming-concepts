package com.snhu.app.util;

import org.slf4j.Logger;

/**
 * ExceptionUtil
 */
public class ExceptionUtil {
	public static void attempt( Runnable run ) {
		try {
			run.run();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void attempt( Logger log, Runnable run ) {
		try {
			run.run();
		} catch ( Exception e ) {
			log.error( "", e);
		}
	}
}