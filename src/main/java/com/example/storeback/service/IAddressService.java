package com.example.storeback.service;

import com.example.storeback.dto.AddressDto;

import java.util.List;

public interface IAddressService {
    List<AddressDto> getAll(Long userId);
    AddressDto addNewAddress(AddressDto addressDto,Long userId);

    AddressDto updateAddress(AddressDto addressDto);

    void deleteAddress(Long addressId);

    void setDefault(Long id, Long userId);
}
