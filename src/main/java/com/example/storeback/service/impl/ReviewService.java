package com.example.storeback.service.impl;

import com.example.storeback.dto.ReviewDto;
import com.example.storeback.dto.response.ReviewResponse;
import com.example.storeback.exception.NotFound;
import com.example.storeback.model.Product;
import com.example.storeback.model.Review;
import com.example.storeback.model.User;
import com.example.storeback.model.pk.ReviewId;
import com.example.storeback.repository.ProductRepository;
import com.example.storeback.repository.ReviewRepository;
import com.example.storeback.repository.UserRepository;
import com.example.storeback.service.IReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public void addReview(ReviewDto reviewDto) {
        Optional<User> user=userRepository.findUserById(reviewDto.getUserId());
        if(user.isEmpty()){
            throw new NotFound("User data not found!");
        }
        Optional<Product> product=productRepository.findById(reviewDto.getProductId());
        if(product.isEmpty()){
            throw new NotFound("Product data not found!");
        }
        Product updateProduct=product.get();
        Integer rc=updateProduct.getReviewCount();
        Double total=updateProduct.getReviewTotal();
        ReviewId reviewId=new ReviewId();
        reviewId.setProduct(product.get());
        reviewId.setUser(user.get());

        Optional<Review> review=reviewRepository.findById(reviewId);
        if(review.isEmpty()){
            Review newReview=new Review();
            newReview.setId(reviewId);
            newReview.setRating(reviewDto.getRating());
            newReview.setComment(reviewDto.getComment());
            updateProduct.setReviewTotal(total+reviewDto.getRating());
            updateProduct.setReviewCount(rc+1);
            productRepository.save(updateProduct);
            reviewRepository.save(newReview);
            return;

        }


        Review rv=review.get();
        updateProduct.setReviewTotal(total-rv.getRating()+ reviewDto.getRating());
        productRepository.save(updateProduct);
        rv.setRating(reviewDto.getRating());
        rv.setComment(reviewDto.getComment());
        reviewRepository.save(rv);


    }

    @Override
    public void updateReview(ReviewDto reviewDto) {

    }

    @Override
    public ReviewDto getReview(Long userId, Long productId) {
        return reviewRepository.getReviewByUserIdAndProductId(userId,productId);
    }
    public String getFormattedDate(ZonedDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdAt.format(formatter);
    }

    @Override
    public List<ReviewResponse> getAllReviewByProductId(Long productId) {
        Optional<Product> product=productRepository.findById(productId);
        if(product.isEmpty()){
            throw new NotFound("Not found product with id: "+productId);
        }

        return reviewRepository.getReviewById_Product(product.get()).stream().map(r->{
            ReviewResponse response=new ReviewResponse();
            response.setUserId(r.getId().getUser().getId());
            response.setComment(r.getComment());
            response.setRating(r.getRating());
            response.setImage("");
            response.setCreateAt(r.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            response.setUsername(r.getId().getUser().getUsername());
            return response;
        }).collect(Collectors.toList());
    }


}
