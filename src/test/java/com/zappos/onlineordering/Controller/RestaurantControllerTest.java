package com.zappos.onlineordering.Controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.zappos.onlineordering.application.OnlineOrderingServiceApplication;
import com.zappos.onlineordering.model.Restaurant;
import com.zappos.onlineordering.model.RestaurantResponse;
import com.zappos.onlineordering.repository.MenuRepository;
import com.zappos.onlineordering.repository.RestaurantRepository;
import com.zappos.onlineordering.service.RestaurantService;
import com.zappos.onlineordering.util.TestUtil;
import com.zappos.onlineordering.utils.Constants;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnlineOrderingServiceApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = { "amazon.dynamodb.endpoint=http://localhost:8000/", "amazon.aws.accesskey=test1",
		"amazon.aws.secretkey=test231" })
public class RestaurantControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RestaurantService restaurantService;

	@Autowired
	MenuRepository menuRepository;

	@Autowired
	RestaurantRepository restaurantRepository;

	RestaurantResponse mockRestaurant;

	Restaurant restaurant;

	@Before
	public void setup() throws Exception {
		restaurant = TestUtil.getSampleRestaurant();
		restaurantRepository.save(restaurant);
		mockRestaurant = new RestaurantResponse();
		mockRestaurant.setId("1");
		mockRestaurant.setName("Chillis");

	}

	@Test
	public void testGetRestaurantMenu() throws Exception {
		Mockito.when(restaurantService.getRestaurantMenu(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(mockRestaurant);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/restaurant/1").accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expected = "{id:\"1\",name:Chillis}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void testCreateRestaurant() throws Exception {
		Mockito.when(restaurantService.createRestaurant(restaurant)).thenReturn(restaurant);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(Constants.ADD_RESTAURANT_BASE_ENDPOINT)
				.accept(MediaType.APPLICATION_JSON).content("{\"address\":\"400, SW 38th Avenu\",\"name\":\"Chillis\"}")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}
}
