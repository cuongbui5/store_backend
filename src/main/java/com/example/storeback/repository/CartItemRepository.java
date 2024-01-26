package com.example.storeback.repository;

import com.example.storeback.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Modifying
    @Query("delete from CartItem c where c.id = :id")
    void deleteCartItemsById(@Param("id") Long id);
    @Modifying
    @Transactional
    @Query("delete from CartItem c where c.cart.id = :id")
    void deleteAllCartItemsByCartId(@Param("id") Long id);


    List<CartItem> findAllByCartId(Long cartId);
}
