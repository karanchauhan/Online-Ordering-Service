package com.zappos.onlineordering.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

@Component
public class TableInit {

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@PostConstruct
	public void init() {
		DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
		TableCollection<ListTablesResult> tables = dynamoDB.listTables();
		Iterator<Table> iter = tables.iterator();
		List<String> tableNames = new ArrayList<>();
		while (iter.hasNext()) {
			tableNames.add(iter.next().getTableName());
		}
		if (!tableNames.contains(Constants.RESTAURANT_TABLE_NAME)) {
			createTable(dynamoDB, Constants.RESTAURANT_TABLE_NAME);
		}
		if (!tableNames.contains(Constants.MENU_TABLE_NAME)) {
			createTable(dynamoDB, Constants.MENU_TABLE_NAME);
		}
	}

	public  void createTable(DynamoDB dynamoDB, String tableName) {
		try {
			List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("Id").withAttributeType("S"));

			List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
			keySchema.add(new KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH));

			CreateTableRequest request = new CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
					.withAttributeDefinitions(attributeDefinitions).withProvisionedThroughput(
							new ProvisionedThroughput().withReadCapacityUnits(5L).withWriteCapacityUnits(6L));

			Table table = dynamoDB.createTable(request);

			table.waitForActive();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
