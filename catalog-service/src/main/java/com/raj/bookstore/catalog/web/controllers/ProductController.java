package com.raj.bookstore.catalog.web.controllers;

import com.raj.bookstore.catalog.domain.PagedResult;
import com.raj.bookstore.catalog.domain.Product;
import com.raj.bookstore.catalog.domain.ProductNotFoundException;
import com.raj.bookstore.catalog.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        // log.info("Fetching products for page: {}", pageNo);
        System.out.println("hellow");
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
       // sleap();
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }

    void sleap(){
        try {
            Thread.sleep(6000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
