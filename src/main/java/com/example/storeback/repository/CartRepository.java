package com.example.storeback.repository;

import com.example.storeback.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
   List<Cart> findByUserId(Long userId);
   boolean existsCartByUserId(Long userId);

   Optional<Cart> findById(Long id);
}
