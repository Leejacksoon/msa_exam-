package com.sparta.msa_exam.product.config.dto;


import com.sparta.msa_exam.product.config.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto implements Serializable {
    Long product_id;
    String name;
    Integer getSupply_price;
    public ProductResponseDto(Product product){
        this.product_id = product.getProduct_id();
        this.name = product.getName();
        this.getSupply_price = product.getSupply_price();
    }
}