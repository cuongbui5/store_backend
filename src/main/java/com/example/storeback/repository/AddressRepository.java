package com.example.storeback.repository;

import com.example.storeback.dto.AddressDto;
import com.example.storeback.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    @Modifying
    @Query("SELECT new com.example.storeback.dto.AddressDto(a.id, a.apartmentNumber, a.street, a.city, b.isDefault) " +
            "FROM Address a " +
            "JOIN UserAddress b ON a.id = b.id.address.id " +
            "WHERE b.id.user.id = :userId")
    List<AddressDto> findAllByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("delete from Address where id = :id")
    void deleteAddressById(@Param("id") Long id);

    @Query("select a from Address a join UserAddress b on a.id=b.id.address.id where b.id.user.id=:userId and b.isDefault=true")
    Address getAddressByUserId(@Param("userId") Long userId);
}
