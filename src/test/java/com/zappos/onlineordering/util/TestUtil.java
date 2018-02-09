package com.zappos.onlineordering.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zappos.onlineordering.model.Menu;
import com.zappos.onlineordering.model.MenuItem;
import com.zappos.onlineordering.model.Restaurant;

public class TestUtil {

	public static Restaurant getSampleRestaurant() {
		Restaurant restaurant = new Restaurant();
		restaurant.setId("testRestaurantID");
		restaurant.setName("Chillis");
		return restaurant;
	}

	public static Menu getSampleMenu() {
		Menu menu = new Menu();
		menu.setMenuId("1");
		menu.setMealType("Lunch");
		List<MenuItem> list = new ArrayList<>();
		list.add(new MenuItem("testID", "testName", "1", "testDescription1"));
		menu.setMenuItems(list);
		menu.setRestaurantId("testRestaurantID");
		return menu;
	}

	public static Menu getSampleMenu2() {
		Menu menu = new Menu();
		menu.setMenuId("2");
		menu.setMealType("Dinner");
		menu.setMenuItems(Arrays.asList(new MenuItem("testID2", "testName2", "2", "testDescription2")));
		menu.setRestaurantId("testRestaurantID2");
		return menu;
	}

	public static MenuItem getSampleMenuItem() {
		return new MenuItem("testMenuItemId", "testMenuItemName", "testMenuItemPrice", "testMenuItemDescription");
	}
}
