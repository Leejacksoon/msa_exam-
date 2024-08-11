package com.sparta.msa_exam.order.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.dto.ProductInOrderResponseDto;
import com.sparta.msa_exam.order.entity.OrderMapping;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.msa_exam.order.entity.QOrder.order;
import static com.sparta.msa_exam.order.entity.QOrderMapping.orderMapping;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public OrderResponseDto searchOrders(Long order_id) {
            QueryResults<OrderMapping> results = queryFactory
                    .select(orderMapping)
                    .from(orderMapping)
                    .join(orderMapping.order,order)
                    .where(order.order_id.eq(order_id))
                    .fetchResults();
            List<ProductInOrderResponseDto> content = results.getResults().stream()
            .map(OrderMapping::toResponseDto)
            .collect(Collectors.toList());
        return new OrderResponseDto(order_id,content);
    }
}