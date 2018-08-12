package com.snhu.app.serialize;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.snhu.app.util.FormatUtil;

/**
 * LocalDateTimeDeserializer
 */
public class LocalDateTimeDeserializer extends StdDeserializer<LocalDateTime>{

	private static final long serialVersionUID = 1L;

	public LocalDateTimeDeserializer(){
		this( null );
	}

	public LocalDateTimeDeserializer( Class< LocalDateTime > t ){
		super( t );
	}

	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return LocalDateTime.parse( p.getText(), FormatUtil.FORMATTER);
	}

	
}