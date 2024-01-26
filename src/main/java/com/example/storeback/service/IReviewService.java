package com.example.storeback.service;

import com.example.storeback.dto.ReviewDto;
import com.example.storeback.dto.response.ReviewResponse;

import java.util.List;

public interface IReviewService {
    void addReview(ReviewDto reviewDto);
    void updateReview(ReviewDto reviewDto);

    ReviewDto getReview(Long userId, Long productId);

    List<ReviewResponse> getAllReviewByProductId(Long productId);
}
