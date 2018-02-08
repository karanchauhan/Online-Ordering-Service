package com.zappos.onlineordering.model;

import java.util.List;

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

	@JsonInclude(Include.NON_NULL)
	List<Menu> menus;

	@JsonInclude(Include.NON_NULL)
	@DynamoDBAttribute
	Double minOrder;

	@JsonInclude(Include.NON_NULL)
	@DynamoDBAttribute
	Double rating;

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", address=" + address + ", menus=" + menus + ", minOrder="
				+ minOrder + ", rating=" + rating + "]";
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

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
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

	public Restaurant(String id, String name, String address, List<Menu> menus, Double minOrder, Double rating) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.menus = menus;
		this.minOrder = minOrder;
		this.rating = rating;
	}

	public Restaurant() {
	}

}
