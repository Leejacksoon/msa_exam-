package com.sparta.msa_exam.product.config.service;

import com.sparta.msa_exam.product.config.dto.ProductRequestDto;
import com.sparta.msa_exam.product.config.dto.ProductResponseDto;
import com.sparta.msa_exam.product.config.entity.Product;
import com.sparta.msa_exam.product.config.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    /**
     * POST /products  상품 추가 API
     * @return ProductResponseDto
     *응답 객체
     * Key   product_id , name   , supply_pricesupply
     * Value Long       , String , Integer
     */
    @CachePut(value = "productCache",key = "#result.product_id")
    public ProductResponseDto appendProduct(ProductRequestDto productRequestDto) {
        Optional<Product> existProduct = productRepository.findByName(productRequestDto.getName());
        if(existProduct.isPresent()){
            throw new IllegalArgumentException("이미있음");
        }
        Product product = Product.createProduct(productRequestDto);
        productRepository.save(product);
        return new ProductResponseDto(product);
    }
    /**
     * GET /products 상품 목록 조회
     * @return List<ProductResponseDto>
     *응답 객체
     * Key   product_id , name   , supply_pricesupply
     * Value Long       , String , Integer
     */
    public List<ProductResponseDto> getProducts() {
        return productRepository.getProducts();
    }

    /**
     * GET /products/{products_id} 상품 목록 단건 조회
     * @return ProductResponseDto
     *응답 객체
     * Key   product_id , name   , supply_pricesupply
     * Value Long       , String , Integer
     */
    @Cacheable(cacheNames = "productCache" , key = "#productId")
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));
        return product.toResponseDto();
    }

}