package com.lcwd.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.store.dtos.ApiResponse;
import com.lcwd.store.dtos.CartDto;
import com.lcwd.store.dtos.CartItemRequest;
import com.lcwd.store.services.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

    // add item to cart
    @Autowired
    private CartService cartService;

//    String userId = "bf7ef4ee-89d8-4605-9f2b-61553659f64c";

    @PostMapping("/{userId}/add-item")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody CartItemRequest cartItemRequest , @PathVariable String userId) {
        CartDto cartDto = cartService.addItemToCart(cartItemRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDto);

    }

    @GetMapping("/{userId}/get")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
    	
    	// can handle no element found exception
        CartDto cart = cartService.getCart(userId);
        return ResponseEntity.ok(cart);

    }

    // TODO: remove item from cart
    // delete.
    @DeleteMapping("/{userId}/{itemId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int itemId , @PathVariable String userId ) {
        cartService.removeItemFromCart(itemId, userId);
        ApiResponse response = ApiResponse.builder()
                .message("item Deleted Success !!")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }

    // TODO: Clear cart
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<ApiResponse> clear(@PathVariable String userId ) {
        cartService.clearCart(userId);
        ApiResponse response = ApiResponse.builder()
                .message("cart cleared Success !!")
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }


}
