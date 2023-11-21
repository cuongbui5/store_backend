package com.example.storeback.service;

import com.example.storeback.model.Product;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

public interface IProductService {
    List<Product> getAll(HttpServletRequest request);
    Product getProductById(Long productId);
}
