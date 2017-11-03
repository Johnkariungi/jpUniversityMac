package com.jpUniversity.repository;

import com.jpUniversity.domain.BookToCartItem;
import com.jpUniversity.domain.CartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookToCartItemRepository extends CrudRepository<BookToCartItem, Long> {
    void deleteByCartItem(CartItem cartItem);
}
