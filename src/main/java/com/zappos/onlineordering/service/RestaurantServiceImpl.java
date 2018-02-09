package com.zappos.onlineordering.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zappos.onlineordering.model.DeleteMenuItemRequest;
import com.zappos.onlineordering.model.Menu;
import com.zappos.onlineordering.model.MenuItem;
import com.zappos.onlineordering.model.Restaurant;
import com.zappos.onlineordering.model.RestaurantResponse;
import com.zappos.onlineordering.model.StatusResponse;
import com.zappos.onlineordering.repository.MenuRepository;
import com.zappos.onlineordering.repository.RestaurantRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public Restaurant createRestaurant(Restaurant restaurant) {
		restaurant.setId(generateId());
		return restaurantRepository.save(restaurant);
	}

	// TODO: Implement exception handling
	@Override
	public RestaurantResponse getRestaurantMenu(String type, String restaurantId) {

		Restaurant restaurant = restaurantRepository.findOne(restaurantId);
		if (null == restaurant) {
			return null; // Restaurant not found.
		}

		RestaurantResponse resp = new RestaurantResponse();
		resp.getMetadata(restaurant);
		List<Menu> menus = new ArrayList<>();

		if (null != type) {
			type = type.toUpperCase();
			if (!restaurant.getMealTypeToMenuIds().containsKey(type)) {
				return null; // Meal type not found
			}
			menus.add(menuRepository.findOne(restaurant.getMealTypeToMenuIds().get(type)));

		} else {
			if (CollectionUtils.isNotEmpty(restaurant.getMealTypeToMenuIds().values())) {
				for (String mealType : restaurant.getMealTypeToMenuIds().keySet()) {
					menus.add(menuRepository.findOne(restaurant.getMealTypeToMenuIds().get(mealType)));
				}
			}

		}
		resp.setMenu(menus);
		return resp;
	}

	@Override
	public Menu createMenu(Menu menu) {

		Restaurant restaurant = restaurantRepository.findOne(menu.getRestaurantId());
		if (null == restaurant) {
			return null;
		}
		String menuId = generateId();
		if (null == restaurant.getMealTypeToMenuIds()) {
			restaurant.setMealTypeToMenuIds(new HashMap<>());
		}
		menu.setMealType(menu.getMealType().toUpperCase());
		restaurant.getMealTypeToMenuIds().put(menu.getMealType(), menuId);
		menu.setMenuId(menuId);

		menu.getMenuItems().forEach(m -> m.setMenuItemId(generateId()));
		restaurantRepository.save(restaurant);
		return menuRepository.save(menu);
	}

	@Override
	public Menu addMenuItem(MenuItem menuItem, String menuId) {
		Menu menu = menuRepository.findOne(menuId);
		menuItem.setMenuItemId(generateId());
		menu.getMenuItems().add(menuItem);
		return menuRepository.save(menu);
	}

	@Override
	public StatusResponse removeMenuItem(DeleteMenuItemRequest req) {
		Menu menu = menuRepository.findOne(req.getMenuId());
		if (null == menu) {
			return null;
		}
		menu.getMenuItems().removeIf(m -> {
			return m.getMenuItemId().equals(req.getMenuItemId());
		});
		menuRepository.save(menu);
		return new StatusResponse("0", "Successfully deleted menu item");
	}

	@Override
	public StatusResponse removeMenu(String id) {
		Menu menu = menuRepository.findOne(id);
		if (null == menu) {
			return null;
		}
		Restaurant restaurant = restaurantRepository.findOne(menu.getRestaurantId());
		restaurant.getMealTypeToMenuIds().remove(menu.getMealType());
		restaurantRepository.save(restaurant);
		menuRepository.delete(id);
		return new StatusResponse("0", "Successfully deleted menu");
	}

	@Override
	public StatusResponse removeRestaurant(String id) {
		Restaurant restaurant = restaurantRepository.findOne(id);
		if (null == restaurant) {
			return null;
		}
		if (restaurant.getMealTypeToMenuIds() != null) {
			for (String mealType : restaurant.getMealTypeToMenuIds().keySet()) {
				menuRepository.delete(restaurant.getMealTypeToMenuIds().get(mealType));
			}
		}
		restaurantRepository.delete(id);
		return new StatusResponse("0", "Successfully deleted restaurant");
	}

	private String generateId() {
		return UUID.randomUUID().toString();
	}
}
