package com.zappos.onlineordering.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.zappos.onlineordering.model.Restaurant;
import com.zappos.onlineordering.repository.RestaurantRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	RestaurantRepository restRepo;

	@Autowired
	DynamoDB db;

	@Override
	public Restaurant createRestaurant(Restaurant req) {
		req.setId(UUID.randomUUID().toString());
		return restRepo.save(req);
	}

}
