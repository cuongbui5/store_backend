package com.example.storeback.dto.response;

import lombok.Data;

import java.util.Date;
@Data
public class ReviewResponse {
    private Long userId;
    private String image;
    private String username;
    private String createAt;
    private Integer rating;
    private String comment;
}
