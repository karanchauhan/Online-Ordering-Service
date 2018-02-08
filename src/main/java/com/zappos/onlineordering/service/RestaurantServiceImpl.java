package com.zappos.onlineordering.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.zappos.onlineordering.model.DeleteMenuItemRequest;
import com.zappos.onlineordering.model.Menu;
import com.zappos.onlineordering.model.MenuItem;
import com.zappos.onlineordering.model.Restaurant;
import com.zappos.onlineordering.model.RestaurantResponse;
import com.zappos.onlineordering.repository.MenuRepository;
import com.zappos.onlineordering.repository.RestaurantRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	RestaurantRepository restRepo;

	@Autowired
	MenuRepository menuRepo;

	@Autowired
	DynamoDB db;

	@Override
	public Restaurant createRestaurant(Restaurant req) {
		req.setId(generateId());
		return restRepo.save(req);
	}

	@Override
	public RestaurantResponse getRestaurantMenu(String type, String id) {

		Restaurant restaurant = restRepo.findOne(id);
		if (null == restaurant) {
			return null; // Restaurant not found
		}

		RestaurantResponse resp = new RestaurantResponse();
		resp.getMetadata(restaurant);
		List<Menu> menus = new ArrayList<>();

		if (null != type) {
			type = type.toUpperCase();
			if (!restaurant.getMealTypes().containsKey(type)) {
				return null; // Meal type not found
			}
			menus.add(menuRepo.findOne(restaurant.getMealTypes().get(type)));

		} else {
			for (String mealType : restaurant.getMealTypes().keySet()) {
				menus.add(menuRepo.findOne(restaurant.getMealTypes().get(mealType)));
			}
		}
		resp.setMenu(menus);
		return resp;
	}

	@Override
	public Menu createMenu(Menu request) {

		Restaurant restaurant = restRepo.findOne(request.getRestaurantId());
		String menuId = generateId();
		if (null == restaurant.getMealTypes()) {
			restaurant.setMealTypes(new HashMap<>());
		}
		request.setMealType(request.getMealType().toUpperCase());
		restaurant.getMealTypes().put(request.getMealType(), menuId);
		request.setMenuId(menuId);

		request.getMenuItems().forEach(m -> m.setMenuItemId(generateId()));
		restRepo.save(restaurant);
		return menuRepo.save(request);
	}

	@Override
	public Menu addMenuItem(MenuItem request, String id) {
		Menu menu = menuRepo.findOne(id);
		request.setMenuItemId(generateId());
		menu.getMenuItems().add(request);
		return menuRepo.save(menu);
	}

	@Override
	public void removeMenuItem(DeleteMenuItemRequest req) {
		Menu menu = menuRepo.findOne(req.getMenuId());
		menu.getMenuItems().removeIf(m -> {
			return m.getMenuItemId().equals(req.getMenuItemId());
		});
		menuRepo.save(menu);
	}

	@Override
	public void removeMenu(String id) {
		Menu menu = menuRepo.findOne(id);
		Restaurant restaurant = restRepo.findOne(menu.getRestaurantId());
		restaurant.getMealTypes().remove(menu.getMealType());
		restRepo.save(restaurant);
		menuRepo.delete(id);
	}

	@Override
	public void removeRestaurant(String id) {
		Restaurant restaurant = restRepo.findOne(id);
		for (String mealType : restaurant.getMealTypes().keySet()) {
			menuRepo.delete(restaurant.getMealTypes().get(mealType));
		}
		restRepo.delete(id);
	}

	private String generateId() {
		return UUID.randomUUID().toString();
	}
}
