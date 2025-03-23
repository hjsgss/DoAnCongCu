package com.GaoQue.service.cart;

import com.GaoQue.model.Cart;
import com.GaoQue.model.CartItem;
import com.GaoQue.model.Product;
import com.GaoQue.repository.CartItemRepository;
import com.GaoQue.repository.CartRepository;
import com.GaoQue.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    @Transactional
    public CartItem addCartItem(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + cartId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cartId, productId);

        if (existingItem != null) {
            // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.updateTotalPrice();
            return cartItemRepository.save(existingItem);
        } else {
            // Nếu sản phẩm chưa tồn tại, thêm mới
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setUnitPrice(product.getPrice());
            newItem.updateTotalPrice();
            return cartItemRepository.save(newItem);
        }
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    @Transactional
    public void removeCartItem(Long cartId, Long productId) {
        CartItem cartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId);

        if (cartItem == null) {
            throw new EntityNotFoundException("CartItem not found in cart with id: " + cartId + " and product id: " + productId);
        }

        cartItemRepository.delete(cartItem);
    }

    /**
     * Lấy tất cả mục trong giỏ hàng
     */
    @Transactional(readOnly = true)
    public List<CartItem> getCartItems(Long cartId) {
        return cartItemRepository.findAllByCartId(cartId);
    }

    /**
     * Xóa tất cả mục trong giỏ hàng
     */
    @Transactional
    public void clearCartItems(Long cartId) {
        cartItemRepository.deleteAllByCartId(cartId);
    }
}
