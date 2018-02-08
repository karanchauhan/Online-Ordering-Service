package com.zappos.onlineordering.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class RestaurantResponse extends Restaurant {

	@JsonInclude(Include.NON_NULL)
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
