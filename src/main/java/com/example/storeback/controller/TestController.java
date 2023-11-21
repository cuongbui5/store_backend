package com.example.storeback.controller;

import com.example.storeback.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("api/v1/test")
public class TestController {
    @GetMapping
    public ResponseEntity<?> test(){
        Random random=new Random();

      return ResponseEntity.ok(new BaseResponse("ok","Test controller!"+ random.nextInt()));
    }



}
