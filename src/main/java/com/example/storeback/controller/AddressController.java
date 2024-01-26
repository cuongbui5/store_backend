package com.example.storeback.controller;

import com.example.storeback.dto.AddressDto;
import com.example.storeback.dto.response.BaseResponse;
import com.example.storeback.service.impl.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;


    @GetMapping("/{userId}")
    public ResponseEntity<List<AddressDto>> getAll(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(addressService.getAll(userId));
    }
    @PostMapping("/{userId}")
    public ResponseEntity<AddressDto> getAll(@PathVariable("userId") Long userId,@RequestBody AddressDto addressDto){
        return ResponseEntity.ok(addressService.addNewAddress(addressDto,userId));
    }

    @PostMapping("/{id}/{userId}")
    public ResponseEntity<BaseResponse> setDefault(@PathVariable("id") Long id, @PathVariable("userId") Long userId){
        addressService.setDefault(id,userId);
        return ResponseEntity.ok(new BaseResponse("ok","set default ok!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id){
        System.out.println(id);
        addressService.deleteAddress(id);
        return ResponseEntity.ok(new BaseResponse("ok","delete ok!"));
    }

    @PutMapping()
    public ResponseEntity<BaseResponse> update(@RequestBody AddressDto addressDto){
        addressService.updateAddress(addressDto);
        return ResponseEntity.ok(new BaseResponse("ok","update ok!"));
    }


}
