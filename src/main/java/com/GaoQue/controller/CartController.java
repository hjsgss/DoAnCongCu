package com.GaoQue.controller;

import com.GaoQue.exceptions.ResourceNotFoundException;
import com.GaoQue.model.Cart;
import com.GaoQue.service.cart.CartItemService;
import com.GaoQue.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // Lấy thông tin giỏ hàng dựa trên ID của giỏ hàng
    @GetMapping("/{cartId}/Cart")
    public ModelAndView getCart(@PathVariable Long cartId) {
        ModelAndView modelAndView = new ModelAndView("/Cart/Cart");
        try {
            Cart cart = cartService.getCart(cartId);
            modelAndView.addObject("message", "Success");
            modelAndView.addObject("cart", cart);
        } catch (ResourceNotFoundException e) {
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("cart", null);
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        }
        return modelAndView;
    }

}
