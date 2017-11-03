package com.jpUniversity.repository;

import com.jpUniversity.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
}
