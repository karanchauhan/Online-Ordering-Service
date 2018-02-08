package com.zappos.onlineordering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zappos.onlineordering.model.Menu;
import com.zappos.onlineordering.model.Restaurant;
import com.zappos.onlineordering.model.RestaurantResponse;
import com.zappos.onlineordering.service.RestaurantService;
import com.zappos.onlineordering.utils.Constants;

@RestController
public class RestaurantController {

	@Autowired
	RestaurantService restaurantService;

	// POST /restaurant creates a restaurant
	// POST /menu/{id} creates a menu of menu items for a particular type
	// POST /menu/{id}/item adds item to menu
	// DELETE /menu/{id}/item removes item from menu
	// GET /restaurant?type= gets all menus for a restaurant for particular type or
	// all types
	// DELETE /restaurant removes a restaurant

	@RequestMapping(value = Constants.RESTAURANT_BASE_ENDPOINT, method = RequestMethod.POST, consumes = Constants.CONTENT_TYPE_JSON)
	public ResponseEntity<?> createRestaurant(@RequestBody Restaurant req) {
		return new ResponseEntity<Restaurant>(restaurantService.createRestaurant(req), HttpStatus.CREATED);
	}

	@RequestMapping(value = Constants.MENU_BASE_ENDPOINT, method = RequestMethod.POST, produces = Constants.CONTENT_TYPE_JSON)
	public ResponseEntity<?> getRestaurant(@RequestBody Menu req) {
		return new ResponseEntity<Menu>(restaurantService.createMenu(req), HttpStatus.CREATED);
	}

	@RequestMapping(value = Constants.RESTAURANT_BASE_ENDPOINT
			+ "/{id}", method = RequestMethod.GET, produces = Constants.CONTENT_TYPE_JSON)
	public ResponseEntity<?> getRestaurantMenu(@RequestParam(value = "type", required = false) String type,
			@PathVariable("id") String id) {
		RestaurantResponse response = restaurantService.getRestaurantMenu(type, id);
		return (null == response) ? new ResponseEntity<RestaurantResponse>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<RestaurantResponse>(response, HttpStatus.OK);
	}

}