package com.jpUniversity.service.impl;

import com.jpUniversity.domain.*;
import com.jpUniversity.repository.BookToCartItemRepository;
import com.jpUniversity.repository.CartItemRepository;
import com.jpUniversity.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookToCartItemRepository bookToCartItemRepository;

    @Override
    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    @Override
    public CartItem updateCartItem(CartItem cartItem) {
        /*total price of each item*/
        BigDecimal bigDecimal = new BigDecimal(cartItem.getBook().getOurPrice()).multiply(new BigDecimal(cartItem.getQty()));
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        cartItem.setSubtotal(bigDecimal);

        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Override
    public CartItem addBookToCartItem(Book book, User user, int qty) {
        List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

        for (CartItem cartItem : cartItemList) {
            if (book.getId() == cartItem.getBook().getId()) { /*check if the item we are trying to add is in the shoppingCart*/
                    cartItem.setQty(cartItem.getQty() + qty); /*set the quantity by increasing the amount*/
                    cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qty)));
                    cartItemRepository.save(cartItem);
                    return cartItem;
            }
        }
        /*if it's not in the shopping cart and is new, create a new cart item*/
        CartItem cartItem = new CartItem();
        cartItem.setShoppingCart(user.getShoppingCart());
        cartItem.setBook(book);

        cartItem.setQty(qty);
        cartItem.setSubtotal(new BigDecimal(book.getOurPrice()).multiply(new BigDecimal(qty)));
        cartItem = cartItemRepository.save(cartItem);

        BookToCartItem bookToCartItem = new BookToCartItem();
        bookToCartItem.setBook(book);
        bookToCartItem.setCartItem(cartItem);
        bookToCartItemRepository.save(bookToCartItem);

        return cartItem;
    }

    @Override
    public CartItem findById(Long id) {
        return cartItemRepository.findOne(id);
    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        bookToCartItemRepository.deleteByCartItem(cartItem);
        cartItemRepository.delete(cartItem);
    }
}
