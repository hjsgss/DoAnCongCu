package com.GaoQue.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private Long cartId; // ID của giỏ hàng
    private List<CartItemDto> items; // Danh sách các sản phẩm trong giỏ hàng
    private BigDecimal totalAmount; // Tổng số tiền của giỏ hàng
    private boolean isEmpty; // Trạng thái giỏ hàng có rỗng không
}
