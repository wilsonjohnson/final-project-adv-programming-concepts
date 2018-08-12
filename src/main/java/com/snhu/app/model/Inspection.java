package com.snhu.app.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Inspection
 */
@JsonPropertyOrder({ "id", "certificate_number", "business_name", "date", "result", "address", "comments", "sector" })
public class Inspection extends Document {
	private String id;
	private long certificateNumber;
	private String businessName;
	private LocalDateTime date;
	private String result;
	private Address address;
	private String comments;
	private String sector;

	/**
	 * @return the businessName
	 */
	@JsonAlias("business_name")
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName the businessName to set
	 */
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	/**
	 * @return the certificateNumber
	 */
	@JsonAlias("certificate_number")
	public long getCertificateNumber() {
		return certificateNumber;
	}

	/**
	 * @param certificateNumber the certificateNumber to set
	 */
	public void setCertificateNumber(long certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}
}