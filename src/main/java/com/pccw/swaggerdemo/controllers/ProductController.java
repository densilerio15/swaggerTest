package com.pccw.swaggerdemo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pccw.swaggerdemo.models.Product;
import com.pccw.swaggerdemo.services.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/product")
@Api(value  = "Digital Store Products API", description = "Contains the endpoints for products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/list", produces = "application/json")
	@ApiOperation(value = "Display all products", response = Product[].class)
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "wala dito"),
			@ApiResponse(code = 200, message = "meron dito"),
	})
	public List<Product> getProducts(){
		return productService.getProductList();
	}
	
//	@GetMapping(value = "/list", produces = "application/json")
//	@ApiOperation(value = "Display all products", response = Product[].class)
//	@ApiResponses(value = {
//			@ApiResponse(code = 404, message = "wala dito"),
//			@ApiResponse(code = 200, message = "meron dito"),
//	})
//	public List<Product> getProducts(){
//		return productService.getProductList();
//	}
	

}
