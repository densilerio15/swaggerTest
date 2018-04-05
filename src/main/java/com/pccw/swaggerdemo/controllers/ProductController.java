package com.pccw.swaggerdemo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pccw.swaggerdemo.models.Product;
import com.pccw.swaggerdemo.services.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/product")
//Sates the description for the product path
@Api(value  = "Digital Store Products API", description = "Contains the endpoints for products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/list", produces = "application/json")
	//Description for each endpoints, also include the type of class for the response of the end point
	@ApiOperation(value = "Display all products", response = Product[].class)
	//We can specify in the document on what message to return for each response code
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "We got nothing for you"),
			@ApiResponse(code = 200, message = "We got it all for you"),
	})
	public List<Product> getProducts(){
		return productService.getProductList();
	}
	
	@GetMapping(value = "/{productId}", produces = "application/json")
	@ResponseBody
	@ApiOperation(value = "Display one product depending on the ID", response = Product.class)
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "Can't find"),
			@ApiResponse(code = 200, message = "Super Okay"),
	})
	public Product getProduct(@PathVariable("productId") String productId){
		return productService.getProduct(productId);
	}
}
