package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.entity.OrderMapping;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInOrderResponseDto {
    private Long id;
    private String name;
    private Long order_id;
    public ProductInOrderResponseDto(OrderMapping orderMapping){
        this.id = orderMapping.getId();
        this.name = orderMapping.getName();
        this.order_id = orderMapping.getOrder().getOrder_id();
    }
}
