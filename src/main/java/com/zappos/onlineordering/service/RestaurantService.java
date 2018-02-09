package com.zappos.onlineordering.service;

import com.zappos.onlineordering.model.DeleteMenuItemRequest;
import com.zappos.onlineordering.model.Menu;
import com.zappos.onlineordering.model.MenuItem;
import com.zappos.onlineordering.model.Restaurant;
import com.zappos.onlineordering.model.RestaurantResponse;
import com.zappos.onlineordering.model.StatusResponse;

public interface RestaurantService {

	public Restaurant createRestaurant(Restaurant request);

	public RestaurantResponse getRestaurantMenu(String menuType, String restaurantId);

	public Menu createMenu(Menu request);

	public Menu addMenuItem(MenuItem request, String id);

	public StatusResponse removeMenuItem(DeleteMenuItemRequest req);

	public StatusResponse removeMenu(String id);

	public StatusResponse removeRestaurant(String id);
}
