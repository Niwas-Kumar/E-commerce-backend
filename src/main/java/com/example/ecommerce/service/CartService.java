package com.example.ecommerce.service;

import com.example.ecommerce.dto.CartDtos.AddToCartRequest;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    @Autowired private UserRepository userRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private ItemRepository itemRepository;
    @Autowired private CartItemRepository cartItemRepository;

    // Get cart by user email
    public Cart getCartByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = Cart.builder().user(user).build();
            return cartRepository.save(newCart);
        });
    }

    // Add item to cart
    public Cart addToCart(String userEmail, AddToCartRequest req) {
        Cart cart = getCartByUserEmail(userEmail);
        Item item = itemRepository.findById(req.itemId()).orElseThrow();

        Optional<CartItem> existing = cart.getItems().stream()
                .filter(ci -> ci.getItem().getId().equals(item.getId()))
                .findFirst();

        CartItem ci;
        if (existing.isPresent()) {
            ci = existing.get();
            ci.setQuantity(ci.getQuantity() + req.quantity());
        } else {
            ci = CartItem.builder()
                    .cart(cart)
                    .item(item)
                    .quantity(req.quantity())
                    .unitPrice(item.getPrice())
                    .build();
            cart.getItems().add(ci);
        }

        cartItemRepository.save(ci);

        // Return updated cart including all items
        return cartRepository.findById(cart.getId()).orElse(cart);
    }

    // Update quantity of an item
    public Cart updateQuantity(String userEmail, Long cartItemId, int quantity) {
        Cart cart = getCartByUserEmail(userEmail);
        CartItem ci = cart.getItems().stream()
                .filter(c -> c.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow();

        if (quantity <= 0) {
            cart.getItems().remove(ci);
            cartItemRepository.delete(ci);
        } else {
            ci.setQuantity(quantity);
            cartItemRepository.save(ci);
        }

        return cartRepository.findById(cart.getId()).orElse(cart);
    }

    // Remove item from cart
    public Cart removeItem(String userEmail, Long cartItemId) {
        return updateQuantity(userEmail, cartItemId, 0);
    }

    // Calculate total cart value
    public BigDecimal cartTotal(Cart cart) {
        return cart.getItems().stream()
                .map(ci -> ci.getUnitPrice().multiply(BigDecimal.valueOf(ci.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
