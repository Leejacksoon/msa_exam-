package com.sparta.msa_exam.order.controller;

import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import com.sparta.msa_exam.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    @Value("${server.port}")
    private String port;


    @PostMapping // 주문추가
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto,
                                                        @RequestHeader(value = "X-User-Id", required = true) String userId) { // 그럼 스트링에 유저 아이디 넣어주자

        return ResponseEntity.ok()
                .header("Server-Port",port)
                .body(orderService.createOrder(orderRequestDto,userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok()
                        .header("Server-Port",port)
                        .body(orderService.getOrderById(orderId));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Long orderId,
                                        @RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok()
                .header("Server-Port",port)
                .body(orderService.updateOrder(orderId, orderRequestDto));
    }



}
