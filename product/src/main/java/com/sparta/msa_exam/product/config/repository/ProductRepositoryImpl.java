package com.sparta.msa_exam.product.config.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.msa_exam.product.config.dto.ProductResponseDto;
import com.sparta.msa_exam.product.config.entity.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.msa_exam.product.config.entity.QProduct.product;


@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public List<ProductResponseDto> getProducts() {
        QueryResults<Product> results = queryFactory
                .selectFrom(product)
                .fetchResults();
        List<ProductResponseDto> content = results.getResults().stream()
                .map(Product::toResponseDto)
                .collect(Collectors.toList());
        return content;
    }
}