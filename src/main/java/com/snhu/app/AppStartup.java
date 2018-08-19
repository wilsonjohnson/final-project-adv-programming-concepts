package com.snhu.app;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.snhu.app.service.StocksDAO;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import static com.snhu.app.util.ExceptionUtil.attempt;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * AppStartup
 */
@Component
public class AppStartup implements
	ApplicationListener< ContextRefreshedEvent >{

	@Autowired
	StocksDAO stocksDAO;

	@Autowired
	Logger log;

	@Autowired
	ApplicationContext context;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		DBObject object;
		try {
			Arrays.stream( context.getResources( "classpath:/com/snhu/app/**" ) ).forEach( resource -> log.info( "Resource: {}", resource ) );
			Path path = context.getResource( "/com/snhu/app/stocks_insert.json" ).getFile().toPath();
			object = (DBObject) JSON.parse( Files.lines(path).collect( Collectors.joining() ) );
		} catch ( Exception e ) {
			log.error( "", e );
			return;
		}

		attempt( log, () -> log.info( "Creating: {}", stocksDAO.create( object ) ) );
		readAndLogTicker();
		attempt( log, () -> log.info( "Updating: {}", stocksDAO.updateVolume( "TEST_TICK", 20053L ) ) );
		readAndLogTicker();
		attempt( log, () -> log.info( "Deleting: {}", stocksDAO.deleteTicker( "TEST_TICK" ) ) );
		readAndLogTicker();
	}

	private void readAndLogTicker(){
		attempt( log, () -> log.info( "Reading:  {}", stocksDAO.readTicker( "TEST_TICK" ).map( Objects::toString ).collect( Collectors.joining( ",\n", "[\n", "\n]") ) ) );
	}
}