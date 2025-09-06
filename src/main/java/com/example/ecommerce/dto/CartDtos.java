package com.example.ecommerce.dto;

public class CartDtos {
    public record AddToCartRequest(Long itemId, int quantity) {}
    public record UpdateQuantityRequest(int quantity) {}
}
