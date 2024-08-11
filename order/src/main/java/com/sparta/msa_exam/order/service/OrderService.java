package com.sparta.msa_exam.order.service;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.entity.Order;
import com.sparta.msa_exam.order.entity.OrderMapping;
import com.sparta.msa_exam.order.repository.OrderMappingRepository;
import com.sparta.msa_exam.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMappingRepository orderMappingRepository;
    private final ProductClient productClient;

    /**
     * POST /order 주문 추가 API
     * @return OrderResponseDto

     *응답 객체 OrderResponseDto
     * Key   orderId , orderItemIds
     * Value Long    , List<ProductInOrderResponseDto>

     *응답 객체   ProductInOrderResponseDto
     *  Key    id    name
     *  Value  Long  String
     */
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, String userId) {
        Order order = Order.createOrder(userId);
        orderRepository.save(order);
        for (Long productId : requestDto.getOrderItemIds()) { // 네임 모아서 product쪽에서 네임으로 in 해서 골라지는 튜플만 갖고오게 하면 좋을듯
            orderMappingRepository.save(
                    OrderMapping.createOrderMapping(
                            productClient.getProduct(productId).getName(),order)
            );
        }
        return orderRepository.searchOrders(order.getOrder_id());
    }

    /**
     * GET /order/{orderId} 주문 단건 조회 API
     * @return OrderResponseDto

     *응답 객체 OrderResponseDto
     * Key   orderId , orderItemIds
     * Value Long    , List<ProductInOrderResponseDto>

     *응답 객체   ProductInOrderResponseDto
     *  Key    id    name
     *  Value  Long  String
     */

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        log.info("캐시저장 ");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("없음"));
        return orderRepository.searchOrders(orderId);
    }


    /**
     * PUT /order/{orderId}  주문에 상품을 추가하는 API
     * @return OrderResponseDto

     *응답 객체 OrderResponseDto
     * Key   orderId , orderItemIds
     * Value Long    , List<ProductInOrderResponseDto>

     *응답 객체   ProductInOrderResponseDto
     *  Key    id    name
     *  Value  Long  String
     */

    @Transactional
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto requestDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("없음"));
        orderMappingRepository.save(
                OrderMapping.createOrderMapping(
                    productClient.getProduct(requestDto.getProduct_id()).getName(), order)
        );
        return orderRepository.searchOrders(order.getOrder_id());
    }
}