package com.example.storeback.dto.response;

import com.example.storeback.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private List<Product> products;
    private int totalPage;
    private int pageIndex;
}
