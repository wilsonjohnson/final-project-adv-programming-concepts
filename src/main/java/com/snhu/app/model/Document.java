package com.snhu.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Document
 */
public class Document {

	@JsonIgnore
	protected Object _id;

	/**
	 * @return the _id
	 */
	@JsonIgnore
	public Object get_id() {
		return _id;
	}
	/**
	 * @param _id the _id to set
	 */
	public void set_id(Object _id) {
		this._id = _id;
	}
}