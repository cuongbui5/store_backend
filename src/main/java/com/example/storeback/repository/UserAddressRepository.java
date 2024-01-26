package com.example.storeback.repository;

import com.example.storeback.model.Address;
import com.example.storeback.model.User;
import com.example.storeback.model.UserAddress;
import com.example.storeback.model.pk.UserAddressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress,UserAddressId> {
    UserAddress getUserAddressByIsDefault(boolean isDefault);
    UserAddress getUserAddressById_AddressAndId_User(Address id_address, User id_user);
    @Modifying
    @Query("delete from UserAddress a where a.id.address.id = :id")
    @Transactional
    void deleteUserAddressByAddressId(@Param("id") Long id);

    UserAddress getUserAddressById_Address(Address address);

}
