package com.example.microservices.product.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product details")
public record ProductResponse(String id, String name, String description,
        String skuCode, BigDecimal price) {
}
