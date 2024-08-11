package com.sparta.msa_exam.product.config.entity;

import com.sparta.msa_exam.product.config.dto.ProductRequestDto;
import com.sparta.msa_exam.product.config.dto.ProductResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    private String name;
    private Integer supply_price;

    public static Product createProduct(ProductRequestDto requestDto) {
        return Product.builder()
                .name(requestDto.getName())
                .supply_price(requestDto.getSupply_price())
                .build();
    }


    public ProductResponseDto toResponseDto() {
        return new ProductResponseDto(this);
    }
}