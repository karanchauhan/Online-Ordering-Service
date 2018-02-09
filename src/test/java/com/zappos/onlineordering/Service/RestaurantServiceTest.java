package com.zappos.onlineordering.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import com.zappos.onlineordering.application.OnlineOrderingServiceApplication;
import com.zappos.onlineordering.model.DeleteMenuItemRequest;
import com.zappos.onlineordering.model.Menu;
import com.zappos.onlineordering.model.MenuItem;
import com.zappos.onlineordering.model.Restaurant;
import com.zappos.onlineordering.model.RestaurantResponse;
import com.zappos.onlineordering.repository.MenuRepository;
import com.zappos.onlineordering.repository.RestaurantRepository;
import com.zappos.onlineordering.service.RestaurantService;
import com.zappos.onlineordering.service.RestaurantServiceImpl;
import com.zappos.onlineordering.util.TestUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnlineOrderingServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "amazon.dynamodb.endpoint=http://localhost:8000/", "amazon.aws.accesskey=test1",
		"amazon.aws.secretkey=test231" })
public class RestaurantServiceTest {

	@Autowired
	MenuRepository menuRepository;

	@Autowired
	RestaurantRepository restaurantRepository;

	@Resource
	RestaurantService restaurantService;

	@Before
	public void setup() throws Exception {
		restaurantService = new RestaurantServiceImpl();

		Field field = ReflectionUtils.findField(RestaurantServiceImpl.class, "restaurantRepository");
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, restaurantService, restaurantRepository);

		field = ReflectionUtils.findField(RestaurantServiceImpl.class, "menuRepository");
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, restaurantService, menuRepository);
	}

	@Test
	public void testRemoveRestaurant() {
		Restaurant restaurant = TestUtil.getSampleRestaurant();
		restaurantRepository.save(restaurant);
		restaurantService.removeRestaurant(restaurant.getId());
		assertNull(restaurantRepository.findOne(restaurant.getId()));
	}

	@Test
	public void testRemoveMenu() {
		Restaurant createdRestaurant = restaurantRepository.save(TestUtil.getSampleRestaurant());
		Menu menu = TestUtil.getSampleMenu();
		menuRepository.save(menu);
		Map<String, String> map = new HashMap<>();
		map.put(menu.getMealType(), menu.getMenuId());
		createdRestaurant.setMealTypes(map);
		restaurantRepository.save(createdRestaurant);

		restaurantService.removeMenu(menu.getMenuId());
		assertNull(menuRepository.findOne(TestUtil.getSampleMenu().getMenuId()));
		restaurantService.removeRestaurant(createdRestaurant.getId());
	}

	// @Test
	// public void testCreateMenu() {
	// final Menu existingMenu = menuRepository.findOne(menu.getMenuId());
	// List<MenuItem> menuItems = existingMenu.getMenuItems();
	// Integer currentMenuItemSize = menuItems.size();
	// MenuItem newMenuItem = new MenuItem("testId1", "testName1", "testPrice1",
	// "testDescription");
	// menuItems.add(newMenuItem);
	// existingMenu.setMenuItems(menuItems);
	// menuRepository.save(existingMenu);
	// assertEquals(currentMenuItemSize + 1,
	// menuRepository.findOne(menu.getMenuId()).getMenuItems().size());
	// }

	@Test
	public void testCreateRestaurant() {
		Restaurant createdRestaurant = restaurantService.createRestaurant(TestUtil.getSampleRestaurant());
		Restaurant getRestaurantFromDb = restaurantRepository.findOne(createdRestaurant.getId());
		restaurantService.removeRestaurant(createdRestaurant.getId());
		assertEquals(getRestaurantFromDb.getId(), createdRestaurant.getId());
	}

	@Test
	public void testGetRestaurantNegative1() {
		RestaurantResponse response = restaurantService.getRestaurantMenu("Dinner", "id");
		assertEquals(response, null);
	}

	@Test
	public void testGetRestaurantNegative2() {
		// check for non existant meal type
		Restaurant createdRestaurant = restaurantService.createRestaurant(TestUtil.getSampleRestaurant());
		Menu menu = TestUtil.getSampleMenu();
		menu.setRestaurantId(createdRestaurant.getId());
		menu = restaurantService.createMenu(menu);
		RestaurantResponse response = restaurantService.getRestaurantMenu("Dinner", createdRestaurant.getId());
		restaurantService.removeRestaurant(createdRestaurant.getId());
		assertEquals(response, null);

	}

	@Test
	public void testGetRestaurantPositive1() {
		Restaurant createdRestaurant = restaurantService.createRestaurant(TestUtil.getSampleRestaurant());
		Menu menu = TestUtil.getSampleMenu();
		menu.setRestaurantId(createdRestaurant.getId());
		menu = restaurantService.createMenu(menu);
		Menu menu2 = TestUtil.getSampleMenu2();
		menu2.setRestaurantId(createdRestaurant.getId());
		menu2 = restaurantService.createMenu(menu2);
		RestaurantResponse response = restaurantService.getRestaurantMenu(null, createdRestaurant.getId());
		assertEquals(response.getMenu().size(), 2);
		restaurantService.removeRestaurant(createdRestaurant.getId());
	}

	@Test
	public void testGetRestaurantPositive2() {
		Restaurant createdRestaurant = restaurantService.createRestaurant(TestUtil.getSampleRestaurant());
		Menu menu = TestUtil.getSampleMenu();
		menu.setRestaurantId(createdRestaurant.getId());
		menu = restaurantService.createMenu(menu);
		Menu menu2 = TestUtil.getSampleMenu2();
		menu2.setRestaurantId(createdRestaurant.getId());
		menu2 = restaurantService.createMenu(menu2);
		RestaurantResponse response = restaurantService.getRestaurantMenu("Lunch", createdRestaurant.getId());
		assertEquals(response.getMenu().get(0).getMealType(), "Lunch".toUpperCase());
		restaurantService.removeRestaurant(createdRestaurant.getId());
	}

	@Test
	public void testAddMenuItem() {
		Menu menu = TestUtil.getSampleMenu();
		menuRepository.save(menu);
		Menu returnedMenu = restaurantService.addMenuItem(TestUtil.getSampleMenuItem(), menu.getMenuId());
		Menu updatedMenu = menuRepository.findOne(menu.getMenuId());
		assertFalse(returnedMenu == null);
		assertFalse(updatedMenu == null);
		assertEquals(returnedMenu.getMenuItems().size(), 2);
		assertEquals(updatedMenu.getMenuItems().size(), 2);
		menuRepository.delete(menu.getMenuId());

	}

	@Test
	public void testRemoveMenuItem() {
		Menu menu = TestUtil.getSampleMenu();
		List<MenuItem> list = menu.getMenuItems();
		list.add(TestUtil.getSampleMenuItem());
		menu.setMenuItems(list);
		menuRepository.save(menu);
		DeleteMenuItemRequest req = new DeleteMenuItemRequest();
		req.setMenuItemId(TestUtil.getSampleMenuItem().getMenuItemId());
		req.setMenuId(menu.getMenuId());
		restaurantService.removeMenuItem(req);
		Menu updatedMenu = menuRepository.findOne(menu.getMenuId());
		assertFalse(updatedMenu == null);
		assertEquals(updatedMenu.getMenuItems().size(), 1);
		menuRepository.delete(menu.getMenuId());

	}

}
