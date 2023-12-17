package com.example.storeback.controller;

import com.example.storeback.dto.response.BaseResponse;
import com.example.storeback.dto.response.ResponseData;
import com.example.storeback.model.Product;
import com.example.storeback.service.impl.CategoryService;
import com.example.storeback.service.impl.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
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
    @PostMapping("/create/{categoryId}")
    public ResponseEntity<?> creatNewProduct(@RequestBody Product product,@PathVariable Long categoryId){
        return ResponseEntity.ok(
                new ResponseData("ok!","success!",productService.createNewProduct(product,categoryId))
        );
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product,@PathVariable Long id){
        return ResponseEntity.ok(
                new ResponseData("ok!","success!",productService.updateProductById(id,product))
        );
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.ok(
                new BaseResponse("ok","delete success!")
        );
    }
}
