package com.example.storeback.controller;

import com.example.storeback.dto.CartItemDto;
import com.example.storeback.dto.request.CreateCartItem;
import com.example.storeback.dto.response.BaseResponse;
import com.example.storeback.dto.response.CartResponse;
import com.example.storeback.model.CartItem;
import com.example.storeback.service.impl.CartItemService;
import com.example.storeback.service.impl.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cartItem")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping("/addToCart")
    public ResponseEntity<BaseResponse> addCart(@RequestBody CreateCartItem cartItem){
        cartItemService.createCartItem(cartItem);
        return ResponseEntity.ok(
                new BaseResponse("ok","Add to cart success!")
        );

    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCartItemsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(
                cartItemService.getCartItemByUserId(userId)
        );
    }

    @PutMapping("/edit")
    public ResponseEntity<BaseResponse> updateCartItem(@RequestBody CartItemDto cartItemDto){
        System.out.println("update:"+cartItemDto.getId());
        cartItemService.updateCartItem(cartItemDto.getQuantity(),cartItemDto.getId());
        return ResponseEntity.ok(
                new BaseResponse("ok","update successful!")
        );
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<BaseResponse> deleteCartItem(@PathVariable Long id){
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok(
                new BaseResponse("ok","delete successful!")
        );
    }
}
