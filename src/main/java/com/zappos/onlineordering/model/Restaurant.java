package com.zappos.onlineordering.model;

import java.util.Map;

import javax.validation.constraints.Size;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@DynamoDBTable(tableName = "Restaurant")
public class Restaurant {

	@DynamoDBHashKey(attributeName = "Id")
	String id;

	@JsonInclude(Include.NON_NULL)
	@DynamoDBAttribute
	String name;

	@JsonInclude(Include.NON_NULL)
	@Size(max = 140)
	@DynamoDBAttribute
	String address;

	@DynamoDBAttribute
	Map<String, String> mealTypes;

	@JsonInclude(Include.NON_NULL)
	@DynamoDBAttribute
	Double minOrder;

	@JsonInclude(Include.NON_NULL)
	@DynamoDBAttribute
	Double rating;

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", address=" + address + ", menuIds=" + mealTypes
				+ ", minOrder=" + minOrder + ", rating=" + rating + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getMinOrder() {
		return minOrder;
	}

	public void setMinOrder(Double minOrder) {
		this.minOrder = minOrder;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Map<String, String> getMealTypes() {
		return mealTypes;
	}

	public void setMealTypes(Map<String, String> mealTypes) {
		this.mealTypes = mealTypes;
	}
}
