package com.example.storeback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long productId;
    private Long userId;
    private Integer rating;
    private String comment;
}
