package com.jpUniversity.service;

import com.jpUniversity.domain.Book;
import com.jpUniversity.domain.CartItem;
import com.jpUniversity.domain.ShoppingCart;
import com.jpUniversity.domain.User;

import java.util.List;

public interface CartItemService {

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);
    CartItem addBookToCartItem(Book book, User user, int qty);
    CartItem findById(Long id);
    void removeCartItem(CartItem cartItem);
}
