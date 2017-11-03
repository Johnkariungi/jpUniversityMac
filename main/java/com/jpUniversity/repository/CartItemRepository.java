package com.jpUniversity.repository;

import com.jpUniversity.domain.CartItem;
import com.jpUniversity.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
