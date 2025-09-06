package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CartDtos.AddToCartRequest;
import com.example.ecommerce.dto.CartDtos.UpdateQuantityRequest;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public Cart getCart(Authentication auth) {
        return cartService.getCartByUserEmail(auth.getName());
    }

    @PostMapping("/add")
    public Cart add(Authentication auth, @RequestBody AddToCartRequest req) {
        return cartService.addToCart(auth.getName(), req);
    }

    @PutMapping("/item/{cartItemId}")
    public Cart updateQty(Authentication auth,
                          @PathVariable Long cartItemId,
                          @RequestBody UpdateQuantityRequest req) {
        return cartService.updateQuantity(auth.getName(), cartItemId, req.quantity());
    }

    @DeleteMapping("/item/{cartItemId}")
    public Cart remove(Authentication auth,
                       @PathVariable Long cartItemId) {
        return cartService.removeItem(auth.getName(), cartItemId);
    }
}
