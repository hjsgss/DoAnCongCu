package com.GaoQue.repository;

import com.GaoQue.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Xóa tất cả các mục trong giỏ hàng dựa trên Cart ID
    void deleteAllByCartId(Long cartId);

    // Tìm tất cả các mục trong giỏ hàng dựa trên Cart ID
    List<CartItem> findAllByCartId(Long cartId);

    // Kiểm tra xem một sản phẩm có tồn tại trong giỏ hàng cụ thể không
    boolean existsByCartIdAndProductId(Long cartId, Long productId);

    // Tìm một mục giỏ hàng cụ thể dựa trên Cart ID và Product ID
    CartItem findByCartIdAndProductId(Long cartId, Long productId);
}
