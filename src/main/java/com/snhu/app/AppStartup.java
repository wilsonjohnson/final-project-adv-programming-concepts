package com.snhu.app;

import com.snhu.app.service.StocksDAO;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

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

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO: Run code
		log.info("Custom Code Run At Startup!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
}