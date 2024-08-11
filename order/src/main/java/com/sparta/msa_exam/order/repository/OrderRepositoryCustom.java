package com.sparta.msa_exam.order.repository;


import com.sparta.msa_exam.order.dto.OrderResponseDto;

public interface OrderRepositoryCustom {
    OrderResponseDto searchOrders(Long order_id);
}
