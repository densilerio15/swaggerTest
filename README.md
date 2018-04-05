# Swagger 2.0 with SpringBoot and JUnit Samples
This project contains the basic setup on how to create a web service with Swagger as the documentation for the APIs. Also includes some unit testing files.

## What does it do?

#### 1) Automated documentation process on the creation of API endpoints. 
We don't need to write a documentation for each endpoints, it will be automatically generated with the corresponding setup on the enpoints(in the controller). 
This documentation will be presented in UI generated also with swagger.

#### 2) Creation of a simple web service with the use of Spring Boot
#### 3) Simple JUnit test for controllers(mocking http requests and service layer)

## Requirements

#### 1) Any Java IDe
#### 2) Maven

## Swagger Procedure

#### 1) Go to https://start.spring.io/ to get a simple scaffolding for the project. For the dependencies add these:
Web
Jersey (JAX-RS)
Spring boot will automatically include the spring core dependencies and spring test.

#### 2) After downloading the project files, import the files as an existing maven project and let maven fetch the dependencies

#### 3) Open pom.xml and add two dependency for swagger to work

```xml
		<dependency>
		<!--  Springfox implementation for swagger -->
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.7.0</version>
		</dependency>
		
		<dependency>
		<!-- Springfox for Swagger UI rendering -->
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.7.0</version>
		</dependency>
 ```
 
 #### 4) Create a swagger config file
(Check the comments on the code)
```java
//Imports omitted

//Tell that this class is configuration class
@Configuration
//To enable swagger 2.0 implementation
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				//Only get controllers and products path
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pccw.swaggerdemo.controllers"))
				.paths(PathSelectors.regex("/product.*"))
				.build()
				.apiInfo(getApiInfo());
	}
	
	private ApiInfo getApiInfo() {
		Contact contact = new Contact("PCCW", "http://blog.PCCW.com", "PCCW@pccw.com");
		return new ApiInfoBuilder()
				.title("Digital Store API")
				.description("Digital Store example API")
				.version("1.0")
				.license("Apache 2.0")
				.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
				.contact(contact)
				.build();
	}

}
```

#### 4) On the controller we can specify springfox annotations to caontrol which details will appear in the documentataion
(Check the comments on the code)
```java
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
```

#### 5) We can also annotate our model classes for the return type
(Check the comments on the code)
```java
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
```

#### 6) We can now start the application and navigate to the generated document go to: http://localhost:8080/swagger-ui.html to view the documentation

## JUnit Procedure

#### 1) If the project comes from Spring Boot it will automatically generate a separate package for our unit tests 
e.g. src/test/java > com.pccw.swaggerdemo.controller

2) Example for testing controller layer (Please check the comments on the code)

```java
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
```
