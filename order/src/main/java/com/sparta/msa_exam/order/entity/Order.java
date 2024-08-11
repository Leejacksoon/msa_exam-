package com.sparta.msa_exam.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    private String name;
    @OneToMany(mappedBy = "order")
    private List<OrderMapping> orderMappingList = new ArrayList<>();

    public static Order createOrder(String name) {
        return Order.builder()
                .name(name)
                .build();
    }
}

