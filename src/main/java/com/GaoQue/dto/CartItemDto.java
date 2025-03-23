package com.GaoQue.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long itemId; // ID của sản phẩm trong giỏ hàng
    private Integer quantity; // Số lượng sản phẩm
    private BigDecimal unitPrice; // Giá mỗi đơn vị sản phẩm
    private BigDecimal totalPrice; // Tổng giá tiền của sản phẩm (unitPrice * quantity)
    private ProductDto product; // Thông tin chi tiết của sản phẩm
}
