package com.example.ecommerce.dto;

import java.math.BigDecimal;

public class ItemDtos {
    public record ItemRequest(String name, String description, BigDecimal price, String category, String imageUrl) {}
}
