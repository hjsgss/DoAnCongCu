package com.GaoQue.repository;

import com.GaoQue.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Tìm giỏ hàng dựa trên ID của người dùng
    Cart findByUserId(Long userId);

    // Kiểm tra xem người dùng đã có giỏ hàng hay chưa
    boolean existsByUserId(Long userId);

    // Xóa giỏ hàng dựa trên ID của người dùng
    void deleteByUserId(Long userId);
}
