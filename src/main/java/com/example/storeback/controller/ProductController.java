package com.example.storeback.controller;

import com.example.storeback.dto.response.ResponseData;
import com.example.storeback.service.impl.CategoryService;
import com.example.storeback.service.impl.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    /*
    @RequestParam(name = "name",required = false) String name,
                                    @RequestParam(name = "page",required = false) int page,
                                    @RequestParam(name = "size",required = false) int size,
                                    @RequestParam(name = "sort",required = false) String field
    * */
    @GetMapping
    public ResponseEntity<?> getAll(HttpServletRequest request){
        return ResponseEntity.ok(
                new ResponseData("ok!","success!",productService.getAll(request))
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        return ResponseEntity.ok(
                new ResponseData("ok!","success!",productService.getProductById(id))
        );
    }
}
