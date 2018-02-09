package com.zappos.onlineordering.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "MenuItem")
public class MenuItem {

	private String menuItemId;

	private String itemName;

	private String itemPrice;

	private String itemDescription;

	public MenuItem() {
		// TODO Auto-generated constructor stub
	}

	public MenuItem(String menuItemId, String itemName, String itemPrice, String itemDescription) {
		super();
		this.menuItemId = menuItemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemDescription = itemDescription;
	}

	@DynamoDBHashKey(attributeName = "Id")
	public String getMenuItemId() {
		return menuItemId;
	}

	public void setMenuItemId(String menuItemId) {
		this.menuItemId = menuItemId;
	}

	@DynamoDBAttribute
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@DynamoDBAttribute
	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	@DynamoDBAttribute
	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

}
