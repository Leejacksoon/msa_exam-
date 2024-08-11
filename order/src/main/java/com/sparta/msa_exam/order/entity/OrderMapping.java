package com.sparta.msa_exam.order.entity;

import com.sparta.msa_exam.order.dto.ProductInOrderResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "ordermapping")
public class OrderMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public static OrderMapping createOrderMapping(String name,Order order) {
        return OrderMapping.builder()
                .name(name)
                .order(order)
                .build();
    }
    public ProductInOrderResponseDto toResponseDto(){
        return new ProductInOrderResponseDto(this);
    }
}
