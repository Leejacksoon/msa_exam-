package com.sparta.msa_exam.product.config.repository;

import com.sparta.msa_exam.product.config.dto.ProductResponseDto;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductResponseDto> getProducts();
}
