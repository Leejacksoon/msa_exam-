package com.sparta.msa_exam.product.config.controller;

import com.sparta.msa_exam.product.config.dto.ProductRequestDto;
import com.sparta.msa_exam.product.config.dto.ProductResponseDto;
import com.sparta.msa_exam.product.config.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @Value("${server.port}")
    private String port;

    @PostMapping("")
    public ResponseEntity<ProductResponseDto> appendProduct(@RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok()
                .header("Server-Port",port)
                .body(productService.appendProduct(productRequestDto));
    }
    @GetMapping("")
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        return ResponseEntity.ok()
                .header("Server-Port",port)
                .body(productService.getProducts());
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        return ResponseEntity.ok()
                .header("Server-Port",port)
                .body(productService.getProductById(productId));
    }
}
