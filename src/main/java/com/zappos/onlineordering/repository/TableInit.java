package com.zappos.onlineordering.repository;

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
import com.zappos.onlineordering.utils.Constants;

@Component
public class TableInit {

	@Autowired
	AmazonDynamoDB db;

	@PostConstruct
	public void init() {
		DynamoDB dynamoDB = new DynamoDB(db);
		TableCollection<ListTablesResult> tables = dynamoDB.listTables();
		Iterator<Table> iter = tables.iterator();
		List<String> tableNames = new ArrayList<>();
		while (iter.hasNext()) {
			tableNames.add(iter.next().getTableName());
		}
		if (!tableNames.contains(Constants.TABLE_RESTAURANT)) {
			createTable(dynamoDB, Constants.TABLE_RESTAURANT);
		}
		if (!tableNames.contains(Constants.TABLE_MENU)) {
			createTable(dynamoDB, Constants.TABLE_MENU);
		}
		if (!tableNames.contains(Constants.TABLE_MENU_ITEM)) {
			createTable(dynamoDB, Constants.TABLE_MENU_ITEM);
		}
	}

	private void createTable(DynamoDB dynamoDB, String tableName) {
		try {
			List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
			attributeDefinitions.add(new AttributeDefinition().withAttributeName("Id").withAttributeType("N"));

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
