package com.inexinnovation.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"customerId","customerFirstName","customerLastName","customerEmail","customerPhone","customerCountry","customerAdditionalComment"})
public class Customer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6362946796247029153L;
	
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	private String customerPhone;
	
	private String customerCountry;
	
	private String customerAdditionalComment;
	
	private String customerDataUsageAgreement;

	private String customerId;
	
	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerAdditionalComment() {
		return customerAdditionalComment;
	}

	public void setCustomerAdditionalComment(String customerAdditionalComment) {
		this.customerAdditionalComment = customerAdditionalComment;
	}

	public String getCustomerDataUsageAgreement() {
		return customerDataUsageAgreement;
	}

	public void setCustomerDataUsageAgreement(String customerDataUsageAgreement) {
		this.customerDataUsageAgreement = customerDataUsageAgreement;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}
	
}
