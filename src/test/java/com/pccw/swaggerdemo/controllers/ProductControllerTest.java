package com.pccw.swaggerdemo.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.pccw.swaggerdemo.models.Product;
import com.pccw.swaggerdemo.services.ProductService;

//Initiate spring tests
@RunWith(SpringRunner.class)
//To tell that this class will test the ProductController class
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	
	//Object used to mock HTTP request(GET, POST, PUT, DELETE)
	@Autowired
	private MockMvc mvc;
	
	//Service to mock
	@MockBean
	private ProductService service;
	
	//Given - Then - When test case
	@Test
	public void givenProducts_whenGetEmployees_thenReturnJsonArrayAndStatusOk() throws Exception {
		
		//given
		List<Product> productList = new ArrayList<Product>();
		for(int i = 0; i <= 3; i++) {
			Product product = new Product("one " + i, "product name " + 1, 10.92 + i);
			productList.add(product);
		}
		
		//Tell that the sevice method will return the product list
		given(service.getProductList()).willReturn(productList);
		
		//when
		//Mock the HTTP request, in this case a GET request which produces JSON
		ResultActions resultActions = mvc.perform(get("/product/list").contentType(MediaType.APPLICATION_JSON));
		
		//then
		//Test cases
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(4)));
	}
	
	@Test
	public void givenProductId_whenGetProduct_thenReturnJSONWithProductIdAndStatusOk() throws Exception {
		//Given
		String productId = "NEW123";
		Product product = new Product(productId, "test name", 1000.00);
		given(service.getProduct(productId)).willReturn(product);
		
		//When
		ResultActions resultActions = mvc.perform(get("/product/{productId}", productId)
									.contentType(MediaType.APPLICATION_JSON));
		
		//Then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.productId", is(productId)));
	}

}
