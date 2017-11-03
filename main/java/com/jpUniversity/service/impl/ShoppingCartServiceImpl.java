package com.jpUniversity.service.impl;

import com.jpUniversity.domain.CartItem;
import com.jpUniversity.domain.ShoppingCart;
import com.jpUniversity.repository.ShoppingCartRepository;
import com.jpUniversity.service.CartItemService;
import com.jpUniversity.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShoppingCartServiceImpl  implements ShoppingCartService{

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemService cartItemService;

   public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {

       BigDecimal cartTotal = new BigDecimal(0);

       List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

       for (CartItem cartItem : cartItemList) {
           if (cartItem.getBook().getInStockNumber() > 0) {
               cartItemService.updateCartItem(cartItem); /*update cart by recalculating the number of cart items*/
               cartTotal = cartTotal.add(cartItem.getSubtotal()); /*recalculate the total*/
           }
       }

       shoppingCart.setGrandTotal(cartTotal); /*change the grand total*/
       shoppingCartRepository.save(shoppingCart); /*save to database*/

       return shoppingCart; /*return shoppingCart*/
   }
}
