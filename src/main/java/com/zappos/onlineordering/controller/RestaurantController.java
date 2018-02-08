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

import com.zappos.onlineordering.model.DeleteMenuItemRequest;
import com.zappos.onlineordering.model.Menu;
import com.zappos.onlineordering.model.MenuItem;
import com.zappos.onlineordering.model.Restaurant;
import com.zappos.onlineordering.model.RestaurantResponse;
import com.zappos.onlineordering.model.StatusResponse;
import com.zappos.onlineordering.service.RestaurantService;
import com.zappos.onlineordering.utils.Constants;

@RestController
public class RestaurantController {

	@Autowired
	RestaurantService restaurantService;

	// POST /restaurant creates a restaurant
	// POST /restaurant/{id} creates a menu for a restaurant of a particular type
	// GET /restaurant?type= gets all menus for a restaurant for particular type or
	// all types
	// POST /menu/{id}/item adds item to menu for menuId = id
	// DELETE /menu/item removes item from menu
	// DELETE /menu/{id} removes menu from restaurant
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
			+ Constants.PATH_VARIABLE_ID, method = RequestMethod.GET, produces = Constants.CONTENT_TYPE_JSON)
	public ResponseEntity<?> getRestaurantMenu(@RequestParam(value = "type", required = false) String type,
			@PathVariable("id") String id) {
		RestaurantResponse response = restaurantService.getRestaurantMenu(type, id);
		return (null == response) ? new ResponseEntity<RestaurantResponse>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<RestaurantResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = Constants.MENU_BASE_ENDPOINT + Constants.PATH_VARIABLE_ID
			+ Constants.ITEM, method = RequestMethod.POST, produces = Constants.CONTENT_TYPE_JSON)
	public ResponseEntity<?> addMenuItem(@PathVariable("id") String id, @RequestBody MenuItem req) {
		return new ResponseEntity<Menu>(restaurantService.addMenuItem(req, id), HttpStatus.CREATED);
	}

	@RequestMapping(value = Constants.MENU_BASE_ENDPOINT + Constants.ITEM, method = RequestMethod.DELETE)
	public ResponseEntity<?> removeMenuItem(@RequestBody DeleteMenuItemRequest req) {
		restaurantService.removeMenuItem(req);
		return new ResponseEntity<StatusResponse>(new StatusResponse("0", "Menu Item deleted successfully"),
				HttpStatus.OK);
	}

	@RequestMapping(value = Constants.MENU_BASE_ENDPOINT + Constants.PATH_VARIABLE_ID, method = RequestMethod.DELETE)
	public ResponseEntity<?> removeMenu(@PathVariable("id") String id) {
		restaurantService.removeMenu(id);
		return new ResponseEntity<StatusResponse>(new StatusResponse("0", "Menu deleted successfully"), HttpStatus.OK);
	}

	@RequestMapping(value = Constants.RESTAURANT_BASE_ENDPOINT
			+ Constants.PATH_VARIABLE_ID, method = RequestMethod.DELETE)
	public ResponseEntity<?> removeRestaurant(@PathVariable("id") String id) {
		restaurantService.removeRestaurant(id);
		return new ResponseEntity<StatusResponse>(new StatusResponse("0", "Restaurant deleted successfully"),
				HttpStatus.OK);
	}

}