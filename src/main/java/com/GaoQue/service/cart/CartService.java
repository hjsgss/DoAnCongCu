package com.GaoQue.service.cart;

import com.GaoQue.exceptions.ResourceNotFoundException;
import com.GaoQue.model.Cart;
import com.GaoQue.model.CartItem;
import com.GaoQue.model.Product;
import com.GaoQue.repository.CartRepository;
import com.GaoQue.repository.ProductRepository;
import com.GaoQue.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + id));
    }

    @Override
    @Transactional
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cart.getItems().clear(); // Xóa tất cả các mục trong giỏ hàng
        cart.setTotalAmount(BigDecimal.ZERO); // Đặt tổng giá thành 0
        cartRepository.save(cart); // Lưu giỏ hàng
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    @Transactional
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        newCart.setTotalAmount(BigDecimal.ZERO);
        return cartRepository.save(newCart).getId(); // Trả về ID của giỏ hàng mới
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

//    @Override
//    @Transactional
//    public Cart addItemToCart(Long cartId, Long productId, int quantity) {
//        Cart cart = getCart(cartId);
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
//
//        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
//        CartItem existingItem = cart.getItems().stream()
//                .filter(item -> item.getProduct().getId().equals(productId))
//                .findFirst()
//                .orElse(null);
//
//        if (existingItem != null) {
//            // Nếu sản phẩm đã có, cập nhật số lượng
//            existingItem.setQuantity(existingItem.getQuantity() + quantity);
//            existingItem.setTotalPrice(); // Cập nhật giá trị tổng
//        } else {
//            // Nếu sản phẩm chưa có, thêm mới
//            CartItem newItem = new CartItem();
//            newItem.setProduct(product);
//            newItem.setQuantity(quantity);
//            newItem.setUnitPrice(product.getPrice());
//            newItem.setTotalPrice(); // Tính tổng giá
//
//            cart.addItem(newItem); // Thêm mục mới vào giỏ hàng
//        }
//        return cartRepository.save(cart); // Lưu giỏ hàng
//    }

//    @Override
//    @Transactional
//    public void removeItemFromCart(Long cartId, Long productId) {
//        Cart cart = getCart(cartId);
//        CartItem itemToRemove = cart.getItems().stream()
//                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Product not found in the cart"));
//
//        cart.removeItem(itemToRemove); // Xóa mục khỏi giỏ hàng
//        cartRepository.save(cart); // Lưu giỏ hàng
//    }
}
