package com.pccw.swaggerdemo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pccw.swaggerdemo.models.Product;


@Service
public class ProductService {
	
	public List<Product> getProductList(){

		List<Product> productList = new ArrayList<Product>();
		for(int i = 0; i <= 3; i++) {
			Product product = new Product("one " + i, "product name " + 1, 10.92 + i);
			productList.add(product);
		}
		
		return productList;
	}
	
	public Product getProduct(String productId) {
		return new Product(productId, "new product with new ID", 100.00);
	}

}
