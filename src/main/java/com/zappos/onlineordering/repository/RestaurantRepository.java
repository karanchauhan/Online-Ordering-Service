package com.zappos.onlineordering.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.zappos.onlineordering.model.Restaurant;

@EnableScan
public interface RestaurantRepository extends CrudRepository<Restaurant, String> {

}
