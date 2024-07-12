package com.deepon.controller;

import com.deepon.model.Cart;
import com.deepon.model.CartItem;
import com.deepon.model.User;
import com.deepon.request.AddCartItemRequest;
import com.deepon.request.UpdateCartItemRequest;
import com.deepon.service.Cart.CartService;
import com.deepon.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PutMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestBody AddCartItemRequest request, @RequestHeader("Authorization") String token) throws Exception {
        CartItem cartItem = cartService.addItemToCart(request,token);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }

    @PutMapping("/cart-item/{id}/remove")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody UpdateCartItemRequest request, @RequestHeader("Authorization") String token) throws Exception {
        CartItem cartItem = cartService.updateCartItemQuantity(request.getCartItemId(),request.getQuantity());
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }


    @DeleteMapping("/cart-item/update")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long id, @RequestHeader("Authorization") String token) throws Exception {
        Cart cart = cartService.removeItemFromCart(id, token);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findCartByUserId(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }








}
