package com.deepon.service.Cart;

import com.deepon.model.Cart;
import com.deepon.model.CartItem;
import com.deepon.request.AddCartItemRequest;

public interface CartService {

    CartItem addItemToCart(AddCartItemRequest addCartItemRequest, String jwt) throws Exception;

    CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws Exception;

    Cart removeItemFromCart(Long cartItemId,String jwt) throws Exception;

    Long calculateCartTotal(Cart cart) throws Exception;

    Cart findCartById(Long id) throws Exception;

    Cart findCartByUserId(Long userId) throws Exception;

    Cart clearCart(Long userId) throws Exception;



}
