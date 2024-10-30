package com.example.microservices.product;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.MongoDBContainer;

import com.example.microservices.product.dto.ProductRequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductApplicationTests {

	@Test
	void contextLoads() {
	}

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.7");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();

		RestAssured.given()
				.contentType("application/json")
				.body(productRequest)
				.when()
				.post("/api/product")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo(productRequest.name()))
				.body("description", Matchers.equalTo(productRequest.description()))
				.body("price", Matchers.is(productRequest.price().intValueExact()));
	}

	private ProductRequest getProductRequest() {
		return new ProductRequest("iPhone 13", "iPhone 13", "iPhone 13", "iPhone 13", BigDecimal.valueOf(1200));
	}

	@Test
	void shouldListNoProducts() throws Exception {
		RestAssured.given()
				.contentType("application/json")
				.when()
				.get("/api/product")
				.then()
				.log().all()
				.statusCode(200);
	}

	@Test
	public void validateSwaggerEndpointReturnsOpenAPIResponse() throws Exception {
		var response = RestAssured.given()
				.when()
				.get("/api-docs")
				.then()
				.log().all()
				.contentType(ContentType.JSON)
				.statusCode(HttpStatus.OK.value())
				.body("openapi", Matchers.notNullValue());
		System.out.println("Response Body is " + response.extract().body().asString());
	}
}
