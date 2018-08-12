package com.snhu.app.serialize;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * LocalDateTimeSerializer
 */
public class LocalDateTimeSerializer extends StdSerializer< LocalDateTime > {

	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

	public LocalDateTimeSerializer(){
		this( null );
	}

	public LocalDateTimeSerializer( Class< LocalDateTime > t ){
		super( t );
	}

	@Override
	public void serialize( LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString( value.format(FORMATTER)  );
	}
}