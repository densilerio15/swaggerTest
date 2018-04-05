package com.pccw.swaggerdemo.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
