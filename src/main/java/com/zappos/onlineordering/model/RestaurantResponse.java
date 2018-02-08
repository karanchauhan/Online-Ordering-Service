package com.zappos.onlineordering.model;

import java.util.List;

public class RestaurantResponse extends Restaurant {

	List<Menu> menu;

	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

	public void getMetadata(Restaurant restaurant) {
		this.setAddress(restaurant.getAddress());
		this.setId(restaurant.getId());
		this.setMinOrder(restaurant.getMinOrder());
		this.setName(restaurant.getName());
		this.setRating(restaurant.getRating());
	}

}
