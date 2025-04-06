package com.GaoQue.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Người bình luận

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // Sản phẩm liên quan đến bình luận
}
