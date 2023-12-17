package com.example.storeback.service;

import com.example.storeback.dto.response.ProductResponse;
import com.example.storeback.model.Product;
import jakarta.servlet.http.HttpServletRequest;

public interface IProductService {
    ProductResponse getAll(HttpServletRequest request);
    Product getProductById(Long productId);

    Product createNewProduct(Product product,Long categoryId);
    Product updateProductById(Long id,Product product);
    void deleteProductById(Long id);
}
