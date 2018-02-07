package com.zappos.onlineordering.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zappos.onlineordering.model.Restaurant;
import com.zappos.onlineordering.utils.Constants;

@RestController
public class RestaurantController {

	// @Autowired
	// MenuItemRepository menuItemsRepository;
	//
	@RequestMapping(value = Constants.RESTAURANT_BASE_ENDPOINT
			+ Constants.CREATE_RESTAURANT_ENDPOINT, method = RequestMethod.POST, produces = Constants.CONTENT_TYPE_JSON)
	public ResponseEntity<?> createRestaurant(@RequestBody Restaurant req) {
		System.out.println(req.toString());
		return new ResponseEntity<String>("Hey", HttpStatus.OK);
	}

	@PostMapping(("/saveMenuItems"))
	public String save() {
		// menuItemsRepository.save(new MenuItem("Pizza","1000","Margarita"));
		// menuItemsRepository.count();
		return "success";
	}
}