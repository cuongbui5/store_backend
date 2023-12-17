package com.example.storeback.repository;

import com.example.storeback.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAll(Specification<Product> productSpecification, Pageable pageable);

    @Override
    Page<Product> findAll(Pageable pageable);
}
