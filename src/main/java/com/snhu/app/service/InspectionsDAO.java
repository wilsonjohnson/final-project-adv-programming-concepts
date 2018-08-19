package com.snhu.app.service;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * InspectionsDAO
 */
@RequestScope
@Component
public class InspectionsDAO implements IDAO {

	DBCollection collection;

	@Autowired
	MongoClient client;

	public InspectionsDAO() {
	}

	public InspectionsDAO( MongoClient client ) throws NullPointerException {
		Objects.requireNonNull( client, () -> "Client provided cannot be null" );
		this.client = client;
		setupCollection();
	}

	@PostConstruct
	public void postConstruct(){
		setupCollection();
	}

	private void setupCollection(){
		collection = client.getDB( "city" ).getCollection( "inspections" );
	}

	@Override
	public DBCollection getCollection() {
		return collection;
	}
}