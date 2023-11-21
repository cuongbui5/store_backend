package com.example.storeback.service.impl;

import com.example.storeback.dto.obj.Page;
import com.example.storeback.model.Product;
import com.example.storeback.repository.ProductRepository;
import com.example.storeback.service.IProductService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    @Override
    public List<Product> getAll(HttpServletRequest request) {
        //test:?name=test&page=1&size=3&sort=+price
        String query=request.getQueryString();
        if(query==null){
            Page pageData=new Page();
            Pageable pageable = PageRequest.of(pageData.getNum(), pageData.getSize());
            return productRepository.findAll(pageable).getContent();
        }



        Map<String,String> data=getFilterQuery(query);
        String size = data.get("size");
        String page = data.get("page");
        String field = data.get("sort");
        Page pageData=new Page();
        if(size!=null){
            pageData.setSize(Integer.parseInt(size));
            data.remove("size");
        }
        if(page!=null){
            pageData.setNum(Integer.parseInt(page)-1);
            data.remove("page");
        }
        if(field!=null){
            data.remove("sort");
        }

        Sort sortProduct=sortBy(field);
        Pageable pageable = PageRequest.of(pageData.getNum(), pageData.getSize(), sortProduct);
        Specification<Product> productSpecification=productSpecification(data);
        return productRepository.findAll(productSpecification, pageable);
    }

    @Override
    public Product getProductById(Long productId) {
        return null;
    }

    public Map<String,String> getFilterQuery(String query){
        return Arrays.stream(query.split("&"))
                .map(s -> s.split("="))
                .filter(arr -> arr.length == 2)
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    public Sort sortBy(String sort){
        if (sort == null || sort.isEmpty()) {
            return Sort.unsorted();
        }
        String field = sort.substring(1);
        Sort.Order order = Sort.Order.by(field);
        if (sort.startsWith("-")) {
            order = order.with(Sort.Direction.DESC);
        } else {
            order = order.with(Sort.Direction.ASC);
        }

        return Sort.by(order);
    }


    public <T> Specification<Product> productSpecification(Map<String, String> filter) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter != null) {
                filter.forEach((key, value) -> {
                    if (key != null && value != null) {
                        try {
                            Double numericValue = Double.parseDouble(value);
                            predicates.add(builder.equal(root.get(key), numericValue));
                        } catch (NumberFormatException e) {
                            String valueIgnoreCase = value.toLowerCase();
                            predicates.add(builder.like(builder.lower(root.get(key)), "%" + valueIgnoreCase.replace("%20"," ") + "%"));
                        }
                    }
                });
            }


            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
