package com.example.microservices.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.microservices.product.model.Product;

import io.micrometer.observation.annotation.Observed;

@Observed
public interface ProductRepository extends MongoRepository<Product, String> {
}
