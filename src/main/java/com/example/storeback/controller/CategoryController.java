package com.example.storeback.controller;

import com.example.storeback.dto.response.BaseResponse;
import com.example.storeback.dto.response.ResponseData;
import com.example.storeback.service.impl.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(
                new ResponseData("ok!","success!",categoryService.getAll())

                );
    }
}
