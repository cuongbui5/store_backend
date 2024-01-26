package com.example.storeback.controller;

import com.example.storeback.dto.ReviewDto;
import com.example.storeback.dto.response.BaseResponse;
import com.example.storeback.dto.response.ReviewResponse;
import com.example.storeback.service.impl.ReviewService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/add-review")
    public ResponseEntity<BaseResponse> addReview(@RequestBody ReviewDto review){
        reviewService.addReview(review);
        return ResponseEntity.ok(new BaseResponse("ok","add review ok!"));

    }

    @GetMapping("/get/{userId}/{productId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable Long productId, @PathVariable Long userId){
        return ResponseEntity.ok(reviewService.getReview(userId,productId));
    }

    @GetMapping("/getAll/{productId}")
    public ResponseEntity<List<ReviewResponse>> getAllReviewByProductId(@PathVariable Long productId){
        return ResponseEntity.ok(reviewService.getAllReviewByProductId(productId));
    }
}
