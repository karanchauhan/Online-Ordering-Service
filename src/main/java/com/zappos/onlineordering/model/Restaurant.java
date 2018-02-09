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
	private String id;

	@JsonInclude(Include.NON_NULL)
	@DynamoDBAttribute
	private String name;

	@JsonInclude(Include.NON_NULL)
	@Size(max = 140)
	@DynamoDBAttribute
	private String address;

	@JsonInclude(Include.NON_NULL)
	@DynamoDBAttribute
	private Map<String, String> mealTypeToMenuIds;

	@JsonInclude(Include.NON_NULL)
	@DynamoDBAttribute
	private Double minOrder;

	@JsonInclude(Include.NON_NULL)
	@DynamoDBAttribute
	private Double rating;

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", address=" + address + ", mealTypeToMenuIds="
				+ mealTypeToMenuIds + ", minOrder=" + minOrder + ", rating=" + rating + "]";
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

	public Map<String, String> getMealTypeToMenuIds() {
		return mealTypeToMenuIds;
	}

	public void setMealTypeToMenuIds(Map<String, String> mealTypeToMenuIds) {
		this.mealTypeToMenuIds = mealTypeToMenuIds;
	}
}
