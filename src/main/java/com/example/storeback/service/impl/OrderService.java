package com.example.storeback.service.impl;

import com.example.storeback.dto.AddressDto;
import com.example.storeback.dto.request.CreateOrderRequest;
import com.example.storeback.exception.NotFound;
import com.example.storeback.model.*;
import com.example.storeback.repository.*;
import com.example.storeback.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final OrderItemService orderItemService;

    @Override
    public Order createOrder(CreateOrderRequest createOrderRequest) {
        try {
            System.out.println(createOrderRequest);
            Long userId=createOrderRequest.getUserId();
            Cart cart= cartRepository.findByUserId(userId).get(0);
            List<CartItem> cartItems=cartItemRepository.findAllByCartId(cart.getId());
            if(cartItems.isEmpty()){
                throw new RuntimeException("Empty cart!");
            }
            Optional<User> user=userRepository.findUserById(userId);
            if(user.isEmpty()){
                throw new NotFound("Not Found user data!");
            }
            Address address= addressRepository.getAddressByUserId(userId);
            if(address==null){
                throw new NotFound("Not found Address");
            }
            Order order=new Order();
            order.setUser(user.get());
            order.setAddress(address);
            order.setStatus(createOrderRequest.getStatus());
            order.setShippingCost(createOrderRequest.getShippingCost());
            order.setTotal(createOrderRequest.getTotal());
            Order newOrder= orderRepository.save(order);
            cartItems.forEach(c->{
                orderItemService.createOrderItem(c,newOrder);
            });
            cartItemRepository.deleteAllCartItemsByCartId(cart.getId());
            return newOrder;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());

        }



    }



    @Override
    public List<Order> getOrderByUserId(Long userId) {
        return null;
    }
}
