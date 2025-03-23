package com.GaoQue.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    /**
     * Cập nhật giá trị `totalPrice` dựa trên `unitPrice` và `quantity`.
     */
    public void updateTotalPrice() {
        if (unitPrice != null) {
            this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        } else {
            this.totalPrice = BigDecimal.ZERO;
        }
    }

    /**
     * Phương thức hỗ trợ khi thay đổi số lượng sản phẩm.
     * @param quantity Số lượng sản phẩm mới.
     * @throws IllegalArgumentException nếu quantity nhỏ hơn hoặc bằng 0.
     */
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.quantity = quantity;
        updateTotalPrice();
    }

    /**
     * Phương thức hỗ trợ khi thay đổi giá mỗi đơn vị sản phẩm.
     * @param unitPrice Giá mỗi đơn vị sản phẩm mới.
     * @throws IllegalArgumentException nếu unitPrice là null hoặc âm.
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Unit price must be a positive value.");
        }
        this.unitPrice = unitPrice;
        updateTotalPrice();
    }
}
