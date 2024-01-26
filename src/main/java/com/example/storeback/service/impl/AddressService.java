package com.example.storeback.service.impl;

import com.example.storeback.dto.AddressDto;
import com.example.storeback.exception.NotFound;
import com.example.storeback.model.Address;
import com.example.storeback.model.User;
import com.example.storeback.model.UserAddress;
import com.example.storeback.model.pk.UserAddressId;
import com.example.storeback.repository.AddressRepository;
import com.example.storeback.repository.UserAddressRepository;
import com.example.storeback.repository.UserRepository;
import com.example.storeback.service.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AddressService implements IAddressService {
    private final AddressRepository addressRepository;
    private final UserAddressRepository userAddressRepository;
    private final UserRepository userRepository;
    @Override
    public List<AddressDto> getAll(Long userId) {
        return addressRepository.findAllByUserId(userId);
    }

    @Override
    public AddressDto addNewAddress(AddressDto addressDto, Long userId) {
        Optional<User> user=userRepository.findUserById(userId);
        if(user.isEmpty()){
            throw new NotFound("User not found with id:"+userId);
        }
        Address address=new Address();
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setApartmentNumber(addressDto.getApartmentNumber());
        Address newAdd=addressRepository.save(address);
        UserAddressId userAddressId=new UserAddressId(newAdd,user.get());
        UserAddress userAddress=new UserAddress();
        userAddress.setIsDefault(false);
        userAddress.setId(userAddressId);
        userAddressRepository.save(userAddress);
        addressDto.setCurrent(false);
        addressDto.setId(newAdd.getId());

        return addressDto;
    }

    @Override
    public AddressDto updateAddress(AddressDto addressDto) {
        Optional<Address> address=addressRepository.findById(addressDto.getId());
        if(address.isEmpty()){
            throw new NotFound("Not found address");
        }
        Address update=address.get();
        update.setApartmentNumber(addressDto.getApartmentNumber());
        update.setCity(addressDto.getCity());
        update.setStreet(addressDto.getStreet());
        addressRepository.save(update);
        return addressDto;
    }

    @Override
    public void deleteAddress(Long addressId) {
        try {
            userAddressRepository.deleteUserAddressByAddressId(addressId);
            addressRepository.deleteAddressById(addressId);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }


    }

    @Override
    public void setDefault(Long id, Long userId) {
        UserAddress userAddress=userAddressRepository.getUserAddressByIsDefault(true);
        if(userAddress!=null){
            userAddress.setIsDefault(false);
            userAddressRepository.save(userAddress);
        }
        Optional<Address> address=addressRepository.findById(id);
        Optional<User> user=userRepository.findUserById(userId);
        if(address.isEmpty()||user.isEmpty()){
            throw new NotFound("Not found data user and address");
        }
        UserAddress updateUserAddress=userAddressRepository.getUserAddressById_AddressAndId_User(address.get(),user.get());
        updateUserAddress.setIsDefault(true);
        userAddressRepository.save(updateUserAddress);

    }
}
