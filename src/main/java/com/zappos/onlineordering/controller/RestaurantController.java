package com.zappos.onlineordering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	private RestaurantService restaurantService;

	@PostMapping(value = Constants.ADD_RESTAURANT_BASE_ENDPOINT, consumes = Constants.CONTENT_TYPE_JSON)
	public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant) {
		if (null == restaurant.getAddress() || null == restaurant.getName()) {
			return getBadRequestOutput();
		}
		return new ResponseEntity<Restaurant>(restaurantService.createRestaurant(restaurant), HttpStatus.CREATED);
	}

	@PostMapping(value = Constants.ADD_MENU_BASE_ENDPOINT, produces = Constants.CONTENT_TYPE_JSON)
	public ResponseEntity<?> createRestaurantMenu(@RequestBody Menu menu) {
		if (null == menu.getRestaurantId() || null == menu.getMealType()) {
			getBadRequestOutput();
		}
		Menu createdMenu = restaurantService.createMenu(menu);
		return createdMenu == null ? new ResponseEntity<RestaurantResponse>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<Menu>(createdMenu, HttpStatus.CREATED);
	}

	@GetMapping(value = Constants.RESTAURANT_BASE_ENDPOINT
			+ Constants.PATH_VARIABLE_ID, produces = Constants.CONTENT_TYPE_JSON)
	public ResponseEntity<?> getRestaurantMenu(@RequestParam(value = "type", required = false) String type,
			@PathVariable("id") String id) {
		RestaurantResponse response = restaurantService.getRestaurantMenu(type, id);
		return (null == response) ? new ResponseEntity<RestaurantResponse>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<RestaurantResponse>(response, HttpStatus.OK);
	}

	@PostMapping(value = Constants.MENU_BASE_ENDPOINT + Constants.PATH_VARIABLE_ID
			+ Constants.ITEM, produces = Constants.CONTENT_TYPE_JSON)
	public ResponseEntity<?> addMenuItem(@PathVariable("id") String id, @RequestBody MenuItem menu) {
		if (null == menu.getItemName() || null == menu.getItemPrice()) {
			return getBadRequestOutput();
		}
		return new ResponseEntity<Menu>(restaurantService.addMenuItem(menu, id), HttpStatus.CREATED);
	}

	@DeleteMapping(value = Constants.MENU_BASE_ENDPOINT + Constants.ITEM)
	public ResponseEntity<?> removeMenuItem(@RequestBody DeleteMenuItemRequest menuItemToDelete) {
		if (null == menuItemToDelete.getMenuItemId() || null == menuItemToDelete.getMenuId()) {
			return getBadRequestOutput();
		}
		StatusResponse resp = restaurantService.removeMenuItem(menuItemToDelete);
		return resp == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<StatusResponse>(resp, HttpStatus.OK);
	}

	@DeleteMapping(value = Constants.MENU_BASE_ENDPOINT + Constants.PATH_VARIABLE_ID)
	public ResponseEntity<?> removeMenu(@PathVariable("id") String id) {
		StatusResponse resp = restaurantService.removeMenu(id);
		return resp == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<StatusResponse>(resp, HttpStatus.OK);
	}

	@DeleteMapping(value = Constants.RESTAURANT_BASE_ENDPOINT + Constants.PATH_VARIABLE_ID)
	public ResponseEntity<?> removeRestaurant(@PathVariable("id") String id) {
		StatusResponse resp = restaurantService.removeRestaurant(id);
		return resp == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<StatusResponse>(resp, HttpStatus.OK);
	}

	private ResponseEntity<?> getBadRequestOutput() {
		return new ResponseEntity<StatusResponse>(
				new StatusResponse("400",
						"Incorrect Input. Check spec on https://github.com/karanchauhan/Online-Ordering-Service"),
				HttpStatus.BAD_REQUEST);

	}

}