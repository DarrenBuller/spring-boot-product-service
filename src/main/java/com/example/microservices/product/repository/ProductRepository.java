package com.example.microservices.product.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.microservices.product.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
