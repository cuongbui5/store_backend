package com.example.storeback.controller;

import com.example.storeback.dto.request.CreateOrderRequest;
import com.example.storeback.dto.response.BaseResponse;
import com.example.storeback.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/payment")
    public ResponseEntity<BaseResponse> payment(@RequestBody CreateOrderRequest createOrderRequest){
        orderService.createOrder(createOrderRequest);
        return ResponseEntity.ok(new BaseResponse("ok","payment ok!"));


    }
}
