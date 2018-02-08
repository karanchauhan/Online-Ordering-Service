package com.zappos.onlineordering.repository;

import javax.annotation.PostConstruct;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

@EnableDynamoDBRepositories(basePackages = "com.zappos.onlineordering.repository")
@Configuration
public class DynamoDBConfig {

	@Value("${amazon.dynamodb.endpoint}")
	private String amazonDynamoDBEndpoint;

	@Value("${amazon.aws.accesskey}")
	private String amazonAWSAccessKey;

	@Value("${amazon.aws.secretkey}")
	private String amazonAWSSecretKey;

	// Create tables if not present

	@PostConstruct
	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());

		if (null != amazonDynamoDBEndpoint && !amazonDynamoDBEndpoint.isEmpty()) {
			amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
		}

		return amazonDynamoDB;
	}

	@Bean
	public DynamoDB dynamoDBClient() {
		return new DynamoDB(amazonDynamoDB());
	}

	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
	}
}
