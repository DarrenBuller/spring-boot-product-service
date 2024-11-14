package com.example.microservices.product.dto;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Details the product to create")
public record ProductRequest(String name, String description,
        String skuCode, BigDecimal price) {
}
