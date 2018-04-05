package com.pccw.swaggerdemo.models;

import io.swagger.annotations.ApiModelProperty;


public class Product {
	
	public Product(String id, String name, Double price){
		this.productId = id;
		this.name = name;
		this.price = price;
	}
	
	//Will display the notes on which each field is supposed to do
	@ApiModelProperty(notes = "The ID of the product, this is a String")
	private String productId;
	@ApiModelProperty(notes = "The name of the product, this is a String")
	private String name;
	@ApiModelProperty(notes = "The price of the product, this is a double")
	private Double price;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

}
