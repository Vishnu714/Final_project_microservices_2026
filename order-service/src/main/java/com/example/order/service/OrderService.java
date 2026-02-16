package com.example.order.service;

import com.example.order.dto.OrderRequestDto;
import com.example.order.dto.OrderResponseDto;
import java.util.List;
public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto request);

    OrderResponseDto getOrder(Long id);

    OrderResponseDto updateStatus(Long id, String newStatus);

    List<OrderResponseDto> getOrdersByProduct(Long productId);

}
