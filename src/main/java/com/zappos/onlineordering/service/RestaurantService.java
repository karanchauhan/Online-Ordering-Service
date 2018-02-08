package com.zappos.onlineordering.service;

import com.zappos.onlineordering.model.DeleteMenuItemRequest;
import com.zappos.onlineordering.model.Menu;
import com.zappos.onlineordering.model.MenuItem;
import com.zappos.onlineordering.model.Restaurant;
import com.zappos.onlineordering.model.RestaurantResponse;

public interface RestaurantService {

	public Restaurant createRestaurant(Restaurant request);

	public RestaurantResponse getRestaurantMenu(String type, String id);

	public Menu createMenu(Menu request);

	public Menu addMenuItem(MenuItem request, String id);

	public void removeMenuItem(DeleteMenuItemRequest req);

	public void removeMenu(String id);

	public void removeRestaurant(String id);
}
