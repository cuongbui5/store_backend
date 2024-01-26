package com.example.storeback.repository;

import com.example.storeback.dto.AddressDto;
import com.example.storeback.dto.ReviewDto;
import com.example.storeback.model.Product;
import com.example.storeback.model.Review;
import com.example.storeback.model.pk.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {


    @Query("SELECT new com.example.storeback.dto.ReviewDto(a.id.product.id, a.id.user.id, a.rating, a.comment) " +
            "FROM Review a " +
            "WHERE a.id.user.id = :userId and a.id.product.id=:productId")
    ReviewDto getReviewByUserIdAndProductId(@Param("userId") Long userId,@Param("productId") Long productId);

    List<Review> getReviewById_Product(Product product);
}
